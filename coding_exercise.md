## Basic task

Please add new field called `description` into `Product` entity. Description value is optional and should be 1 - 255
characters long, if present.
There should be new JPA repository method to find Products having description `like` given value. This method could
return 0 to N products and should be exposed via REST API (in `ProductController`).
Please initialize the DB with some test data.

## Advanced task

### Step 1
Create `@RestController` that will expose new endpoint to search Clients by code of Product. It should look for Products
whose code starts by given keyword, ignoring case. The result should be a list of Clients sorted by last name. Cover the
feature by tests.

### Step 2
Add pagination support. Client should be able to set page number, page size and sort. Add/update tests.
> Hint: check Spring's `Pageable`

### Step 3
Please enhance each returned Client JSON by new key `productCode` that will contain code of the Product that
caused addition of this Client into result list. In case the Client has reference to multiple Products matching the
search criteria, concatenate names of selected products using semicolon as separator. Add/update tests.
> Hint: could be solved using transient fields or Spring Data's Projections.