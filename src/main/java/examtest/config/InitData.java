package examtest.config;


import examtest.delivery.Delivery;
import examtest.delivery.DeliveryRepository;
import examtest.product.Product;
import examtest.product.ProductRepository;
import examtest.productorder.ProductOrder;
import examtest.productorder.ProductOrderRepository;
import examtest.van.Van;
import examtest.van.VanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private ProductOrderRepository productOrderRepository;
    @Autowired
    private VanRepository vanRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Creating initial data ...");
        createInitialData();
    }

    private void createInitialData(){
        System.out.println("Creating initial data ...");

        List<Product> products = new ArrayList<>();
        products.add(new Product("Kyllingeunderlår", 20.0,450));
        products.add(new Product("Kyllingefilet", 30.0, 500));
        products.add(new Product("Kyllingelår", 25.0, 400));
        products.add(new Product("Kyllingefars", 15.0, 300));
        products.add(new Product("Oksesteak", 50.0, 350));
        products.add(new Product("Svinekotelet", 35.0, 300));
        products.add(new Product("Lammekotelet", 45.0, 250));
        products.add(new Product("Fiskefilet", 40.0, 200));
        products.add(new Product("Reje", 60.0, 100));
        products.add(new Product("Hummer", 100.0, 500));
        products.add(new Product("Krabbe", 80.0, 400));
        products.add(new Product("Tun", 70.0, 300));
        products.add(new Product("Laks", 65.0, 250));
        products.add(new Product("Ørred", 55.0, 200));
        // Gem hvert produkt i databasen
        productRepository.saveAll(products);

        // Create some Delivery objects
        Delivery delivery1 = new Delivery(LocalDate.now(), "Warehouse1", "Destination1", new ArrayList<>());
        Delivery delivery2 = new Delivery(LocalDate.now(), "Warehouse2", "Destination2", new ArrayList<>());

        // Save Delivery objects in the database
        deliveryRepository.save(delivery1);
        deliveryRepository.save(delivery2);

        // Create some ProductOrder objects
        ProductOrder productOrder1 = new ProductOrder(10, productRepository.findById(1).orElseThrow(), delivery1);
        ProductOrder productOrder2 = new ProductOrder(20, productRepository.findById(2).orElseThrow(), delivery2);

        // Add ProductOrder objects to Delivery objects
        delivery1.getProductOrders().add(productOrder1);
        delivery2.getProductOrders().add(productOrder2);

        // Save ProductOrder objects in the database
        productOrderRepository.save(productOrder1);
        productOrderRepository.save(productOrder2);

        // Create some ProductOrder objects without a Delivery
        for (int i = 3; i <= 14; i++) {
            ProductOrder productOrder = new ProductOrder(10, productRepository.findById(i).orElseThrow(), null);
            productOrderRepository.save(productOrder);
        }

        Van van1 = new Van("Brand1", "Model1", 1000);
        Van van2 = new Van("Brand2", "Model2", 2000);
        Van van3 = new Van("Brand3", "Model3", 3000);
        Van van4 = new Van("Brand4", "Model4", 1);

        vanRepository.save(van1);
        vanRepository.save(van2);
        vanRepository.save(van3);
        vanRepository.save(van4);
    }
}