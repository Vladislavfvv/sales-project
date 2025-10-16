package org.example;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


/**
 * You have a list of Orders (List<Order>) received by the online store. You need to analyze this list using StreamAPI to collect different business metrics
 * Metrics
 * + List of unique cities where orders came from
 * + Total income for all completed orders
 * + The most popular product by sales
 * + Average check for successfully delivered orders
 * + Customers who have more than 5 orders
 * + All metrics should be covered with unit tests using JUnit 5
 */
public class Main {
    public static void main(String[] args) {

        Customer customerAnton = new Customer("1", "Anton", "anton@mail.ru", LocalDateTime.now(), 25, "Moscow");
        Customer customerIvan = new Customer("2", "Ivan", "ivan@yandex.ru", LocalDateTime.now().minusDays(10L), 30, "Minsk");
        Customer customerOlga = new Customer("3", "Olga", "olga@yandex.ru", LocalDateTime.now().minusMinutes(100L), 33, "Moscow");
        Customer customerRoma = new Customer("4", "Roman", "romka@yandex.ru", LocalDateTime.now().minusYears(1L), 27, "Smolensk");
        Customer customerGora = new Customer("5", "Georgiy", "gorik@yandex.ru", LocalDateTime.now().minusDays(25L), 25, "Moscow");
        Customer customerSveta = new Customer("6", "Sveta", "svetik@yandex.ru", LocalDateTime.now().minusDays(30L), 30, "Minsk");


        List<OrderItem> listOrderItems1 = Arrays.asList(
                new OrderItem("T-shirt", 3, 100.0, Category.CLOTHING),
                new OrderItem("Pants", 3, 360.0, Category.CLOTHING),
                new OrderItem("Cream", 2, 400.0, Category.BEAUTY),
                new OrderItem("Phone", 1, 2000.0, Category.ELECTRONICS)
        );

        List<OrderItem> listOrderItems2 = Arrays.asList(
                new OrderItem("T-shirt", 3, 100.0, Category.CLOTHING),
                new OrderItem("TV", 2, 2500.60, Category.ELECTRONICS)
        );

        List<OrderItem> listOrderItems3 = Arrays.asList(
                new OrderItem("Shampoo", 5, 220.0, Category.BEAUTY),
                new OrderItem("Cream", 2, 400.0, Category.BEAUTY),
                new OrderItem("Phone", 1, 3000.0, Category.ELECTRONICS)
        );
        List<OrderItem> listOrderItems4 = Arrays.asList(
                new OrderItem("Microwave oven", 1, 1200.0, Category.HOME),
                new OrderItem("Java-book", 1, 300.0, Category.BOOKS),
                new OrderItem("Pants", 3, 360.0, Category.CLOTHING)
        );

        List<OrderItem> listOrderItems5 = Arrays.asList(
                new OrderItem("Bear", 1, 150.0, Category.TOYS),
                new OrderItem("Monkey", 1, 120.0, Category.TOYS),
                new OrderItem("Shampoo", 5, 220.0, Category.BEAUTY)
        );


        List<Order> listOrder = Arrays.asList(
                new Order("1", LocalDateTime.now(), customerAnton, listOrderItems1, OrderStatus.DELIVERED),
                new Order("2", LocalDateTime.now(), customerIvan, listOrderItems2, OrderStatus.DELIVERED),
                new Order("3", LocalDateTime.now(), customerOlga, listOrderItems3, OrderStatus.NEW),
                new Order("4", LocalDateTime.now(), customerRoma, listOrderItems4, OrderStatus.PROCESSING),
                new Order("5", LocalDateTime.now(), customerGora, listOrderItems5, OrderStatus.DELIVERED),
                new Order("6", LocalDateTime.now(), customerIvan, listOrderItems1, OrderStatus.DELIVERED),
                new Order("7", LocalDateTime.now(), customerIvan, listOrderItems2, OrderStatus.DELIVERED),
                new Order("8", LocalDateTime.now(), customerIvan, listOrderItems2, OrderStatus.DELIVERED),
                new Order("9", LocalDateTime.now(), customerIvan, listOrderItems3, OrderStatus.DELIVERED),
                new Order("10", LocalDateTime.now(), customerIvan, listOrderItems4, OrderStatus.DELIVERED),
                new Order("11", LocalDateTime.now(), customerIvan, listOrderItems5, OrderStatus.DELIVERED)
        );

        OrderAnalysisService analysisService = new OrderAnalysisService(listOrder);

        // 1. List of unique cities
        Set<String> cities = analysisService.getUniqueCities();
        System.out.println("\nList of unique cities: " + cities);

        // 2. Total income for completed orders
        double totalIncome = analysisService.getTotalIncomeForCompletedOrders();
        System.out.println("Total income for all completed orders: " + totalIncome);

        // 3. Most popular product
        String mostPopularProduct = analysisService.getMostPopularProduct();
        System.out.println("Best product: " + mostPopularProduct);

        // 4. Average check for delivered orders
        double averageCheck = analysisService.getAverageCheckForDeliveredOrders();
        System.out.println("Average order: " + averageCheck);

        // 5. Customers with more than 5 orders
        List<Customer> topCustomers = analysisService.getCustomersWithMoreThan5Orders();
        System.out.println("Customers with more than 5 orders:");
        topCustomers.forEach(customer -> System.out.println("Customer: " + customer.getName()));

    }
}