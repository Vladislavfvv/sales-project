package org.example;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderAnalysisService {
    private final List<Order> orders;

    public OrderAnalysisService(List<Order> orders) {
        this.orders = orders;
    }

    // 1. List of unique cities where orders came from
    public Set<String> getUniqueCities() {
        return orders.stream()
                .map(order -> order.getCustomer().getCity())
                .collect(Collectors.toSet());//set список уникальных значение, то в него преобразуем
    }

    // 2. Total income for all completed orders
    public double getTotalIncomeForCompletedOrders() {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())//преобразование каждой товара в заказе в один поток
                .mapToDouble(orderItem -> orderItem.getPrice() * orderItem.getQuantity())//итого по заказу
                .sum();
    }

    // 3. The most popular product by sales
    public String getMostPopularProduct() {
        Map<String, Integer> productSales = orders.stream()
                .flatMap(order -> order.getItems().stream())//преобразование  в поток товаров из заказов/собираем мапу
                .collect(Collectors.groupingBy(
                        OrderItem::getProductName,
                        Collectors.summingInt(OrderItem::getQuantity)
                ));

        return productSales.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())//итерируясь по мапу ищем максимальное значение
                .map(Map.Entry::getKey)//получить значение по ключу
                .orElseThrow(() -> new IllegalStateException("No products found"));
    }

    // 4. Average check for successfully delivered orders
    public double getAverageCheckForDeliveredOrders() {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getItems().stream()//преобразование каждого Order в сумму заказа для подсчета суммы всего заказа
                        .mapToDouble(item -> item.getPrice() * item.getQuantity())//подсчет стоимости товара
                        .sum())
                .average()
                .orElseThrow(() -> new IllegalStateException("No delivered orders found"));
    }

    // 5. Customers who have more than 5 orders
    public List<Customer> getCustomersWithMoreThan5Orders() {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.counting()))//группировка по кастомеру
                .entrySet().stream()//подсчет стоимости товара//переводим мапу в поток пар
                .filter(entry -> entry.getValue() > 5)
                .map(Map.Entry::getKey)//берем ключи из мапы
                .collect(Collectors.toList());
    }
}
