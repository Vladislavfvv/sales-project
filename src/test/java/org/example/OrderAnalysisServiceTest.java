package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderAnalysisServiceTest {
    private List<Order> listOrder;
    private OrderAnalysisService analysisService;

    @BeforeEach
    void setUp() {
        // Создаем тестовые данные (такие же как в вашем main методе)
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

        listOrder = Arrays.asList(
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

        analysisService = new OrderAnalysisService(listOrder);
    }

    @Test
    void testGetUniqueCities() {
        // When
        Set<String> cities = analysisService.getUniqueCities();

        // Then
        assertEquals(3, cities.size());
        assertTrue(cities.contains("Moscow"));
        assertTrue(cities.contains("Minsk"));
        assertTrue(cities.contains("Smolensk"));
    }

    @Test
    void testGetTotalIncomeForCompletedOrders() {
        // When
        double totalIncome = analysisService.getTotalIncomeForCompletedOrders();

        // Then
        assertEquals(34483.60, totalIncome, 0.01);
    }

    @Test
    void testGetMostPopularProduct() {
        // When
        String mostPopularProduct = analysisService.getMostPopularProduct();

        // Then - Shampoo должен быть самым популярным (20 шт против 15 у T-shirt)
        assertEquals("Shampoo", mostPopularProduct);
    }

    @Test
    void testGetAverageCheckForDeliveredOrders() {
        // When
        double averageCheck = analysisService.getAverageCheckForDeliveredOrders();

        // Then
        assertEquals(3831.51, averageCheck, 0.01);
    }

    @Test
    void testGetCustomersWithMoreThan5Orders() {
        // When
        List<Customer> customers = analysisService.getCustomersWithMoreThan5Orders();

        // Then
        assertEquals(1, customers.size());
        assertEquals("Ivan", customers.get(0).getName());
    }

    @Test
    void testEmptyOrderList() {
        // Given
        OrderAnalysisService emptyService = new OrderAnalysisService(Arrays.asList());

        // When & Then
        assertTrue(emptyService.getUniqueCities().isEmpty());
        assertEquals(0.0, emptyService.getTotalIncomeForCompletedOrders());
        assertThrows(IllegalStateException.class, emptyService::getMostPopularProduct);
        assertThrows(IllegalStateException.class, emptyService::getAverageCheckForDeliveredOrders);
        assertTrue(emptyService.getCustomersWithMoreThan5Orders().isEmpty());
    }

}