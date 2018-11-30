package cache;

import controllers.ProductController;
import controllers.UserController;
import model.Product;
import model.User;
import utils.Config;

import java.util.ArrayList;

//TODO: Build this cache and use it.:FIXED
public class UserCache {


    // List of products
    private ArrayList<User> users;

    // Time cache should live
    private long ttl;

    // Sets when the cache has been created
    private long created;
// nedenstående er blevet erklæret i vores config klasse

    public UserCache() {
        this.ttl = Config.getUserTtl();
    }

    public ArrayList<User> getUsers(Boolean forceUpdate) {

        // If we whis to clear cache, we can set force update.
        // Otherwise we look at the age of the cache and figure out if we should update.
        // If the list is empty we also check for new products
        if (forceUpdate
                || ((this.created + this.ttl) <= (System.currentTimeMillis() / 1000L))
                || this.users.isEmpty()) {

            // Get products from controller, since we wish to update.
            ArrayList<User> users = UserController.getUsers();

            // Set products for the instance and set created timestamp
            this.users = users;
            this.created = System.currentTimeMillis() / 1000L;
            //tester om cachen bliver brugt -- cache må kun udskrive en gang da den skal køre mindst en gang, udskrives den fleregange er den ikke anvendt.
            System.out.println("cache is not used");
        }

        // Return the documents
        return this.users;
    }
}


