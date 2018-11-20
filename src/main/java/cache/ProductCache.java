package cache;

import controllers.ProductController;
import java.util.ArrayList;
import model.Product;
import utils.Config;

// husk at snak om salt i opgaven at man kunne gemme den et sikre sted.

public class ProductCache {

  // List of products
  private ArrayList<Product> products;

  // Time cache should live
  private long ttl;

  // Sets when the cache has been created
  private long created;

  public ProductCache() {
    this.ttl = Config.getProductTtl();
  }

  public ArrayList<Product> getProducts(Boolean forceUpdate) {

    // If we whis to clear cache, we can set force update.
    // Otherwise we look at the age of the cache and figure out if we should update.
    // If the list is empty we also check for new products

    //pilen er vendt <

    if (forceUpdate
        || ((this.created + this.ttl) <= (System.currentTimeMillis() / 1000L))
        || this.products.isEmpty()) {

      // Get products from controller, since we wish to update.
      ArrayList<Product> products = ProductController.getProducts();

      // Set products for the instance and set created timestamp
      this.products = products;
      this.created = System.currentTimeMillis() / 1000L;
     //tester om cachen bliver brugt
      System.out.println("cache is not used");
    }

    // Return the documents
    return this.products;
  }
}
