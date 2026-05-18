package dk.cngroup.wishlist.service;

import dk.cngroup.wishlist.controller.dto.WishlistPatchRequest;
import dk.cngroup.wishlist.controller.dto.WishlistRequest;
import dk.cngroup.wishlist.controller.dto.WishlistResponse;
import dk.cngroup.wishlist.entity.Client;
import dk.cngroup.wishlist.entity.ClientRepository;
import dk.cngroup.wishlist.entity.Product;
import dk.cngroup.wishlist.entity.ProductRepository;
import dk.cngroup.wishlist.entity.Wishlist;
import dk.cngroup.wishlist.entity.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static dk.cngroup.wishlist.controller.ControllerSupport.ifDefined;
import static dk.cngroup.wishlist.controller.ControllerSupport.resourceNotFound;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<WishlistResponse> getWishlists() {
        return wishlistRepository.findAll(Sort.by(ASC, "id")).stream().map(WishlistResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public WishlistResponse getWishlist(Long id) {
        return wishlistRepository.findById(id)
                .map(WishlistResponse::from)
                .orElseThrow(() -> resourceNotFound("wishlist", id));
    }

    @Transactional
    public WishlistResponse createWishlist(WishlistRequest request) {
        return WishlistResponse.from(wishlistRepository.saveAndFlush(
                new Wishlist(getClient(request.getClientId()), getProducts(request.getProductIds()))
        ));
    }

    @Transactional
    public WishlistResponse replaceWishlist(Long id, WishlistRequest request) {
        Wishlist wishlist = wishlistRepository.findById(id).orElseGet(Wishlist::new);
        wishlist.setClient(getClient(request.getClientId()));
        wishlist.getProducts().clear();
        wishlist.getProducts().addAll(getProducts(request.getProductIds()));
        return WishlistResponse.from(wishlistRepository.saveAndFlush(wishlist));
    }

    @Transactional
    public WishlistResponse updateWishlist(Long id, WishlistPatchRequest request) {
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(() -> resourceNotFound("wishlist", id));
        ifDefined(request.getClientId(), clientId -> wishlist.setClient(clientId == null ? null : getClient(clientId)));
        ifDefined(request.getProductIds(), productIds -> {
            wishlist.getProducts().clear();
            wishlist.getProducts().addAll(getProducts(productIds));
        });
        return WishlistResponse.from(wishlistRepository.saveAndFlush(wishlist));
    }

    @Transactional
    public void deleteWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }

    private Client getClient(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> resourceNotFound("client", id));
    }

    private List<Product> getProducts(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        var productsById = productRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        return ids.stream()
                .map(productId -> {
                    Product product = productsById.get(productId);
                    if (product == null) {
                        throw resourceNotFound("product", productId);
                    }
                    return product;
                })
                .toList();
    }
}
