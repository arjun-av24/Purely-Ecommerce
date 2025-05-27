package com.purely.services.impls;

import com.purely.dtos.responses.ApiResponseDto;
import com.purely.dtos.requests.OrderRequestDto;
import com.purely.enums.EOrderPaymentStatus;
import com.purely.enums.EOrderStatus;
import com.purely.exceptions.CartNotFoundException;
import com.purely.exceptions.OrderNotFoundException;
import com.purely.exceptions.ServiceLogicException;
import com.purely.exceptions.UserNotFoundException;
import com.purely.modals.Cart;
import com.purely.modals.Order;
import com.purely.repositories.CartRepository;
import com.purely.repositories.OrderRepository;
import com.purely.repositories.ProductRepository;
import com.purely.repositories.UserRepository;
import com.purely.services.NotificationService;
import com.purely.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private NotificationService notificationService;


    public ResponseEntity<ApiResponseDto<?>> createOrder(OrderRequestDto request) throws UserNotFoundException, ServiceLogicException, CartNotFoundException {

        try {

            Cart cart = this.cartRepository.findById(request.getCartId()).orElseThrow(() -> new CartNotFoundException("No cart found for id " + request.getCartId()));

            if (cart.getCartItems().isEmpty()) {
                throw new CartNotFoundException("No items in the cart!");
            }

            Order order = orderRequestDtoToOrder(request, cart);

            order = orderRepository.insert(order);
            clearCart(cart);
            notificationService.sendOrderConfirmationEmail(userRepository.findById(cart.getUserId()).orElse(null), order);

            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .message("Order has been successfully placed!")
                            .build()
            );
        }catch (CartNotFoundException e) {
            throw new CartNotFoundException(e.getMessage());
        }catch (Exception e) {
            log.error("Failed to create order: " + e.getMessage());
            throw new ServiceLogicException("Unable to proceed order!");
        }
    }

    public ResponseEntity<ApiResponseDto<?>> getOrdersByUser(String userId) throws UserNotFoundException, ServiceLogicException {
        try {
            if (userRepository.existsById(userId)) {
                Set<Order> orders = orderRepository.findByUserIdOrderByIdDesc(userId);
                return ResponseEntity.ok(
                        ApiResponseDto.builder()
                                .isSuccess(true)
                                .message(orders.size() + " orders found!")
                                .response(orders)
                                .build()
                );

            }
        }catch (Exception e) {
            log.error("Failed to create order: " + e.getMessage());
            throw new ServiceLogicException("Unable to find orders!");
        }
        throw new UserNotFoundException("User not found wit id " + userId);
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> cancelOrder(String orderId) throws ServiceLogicException, OrderNotFoundException {
        try {
            if(orderRepository.existsById(orderId)) {
                Order order = orderRepository.findById(orderId).orElse(null);
                order.setOrderStatus(EOrderStatus.CANCELLED);
                orderRepository.save(order);
                return ResponseEntity.ok(
                        ApiResponseDto.builder()
                                .isSuccess(true)
                                .message("Order successfully cancelled")
                                .build()
                );
            }
        }catch (Exception e) {
            log.error("Failed to create order: " + e.getMessage());
            throw new ServiceLogicException("Unable to find orders!");
        }
        throw new OrderNotFoundException("Order not found with id " + orderId);
    }

    private void clearCart(Cart cart) {
        cart.setCartItems(new HashSet<>());
        cartRepository.save(cart);
    }

    private Order orderRequestDtoToOrder(OrderRequestDto request, Cart cart) {
        return Order.builder()
                .userId(cart.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .phoneNo(request.getPhoneNo())
                .placedOn(LocalDateTime.now())
                .orderStatus(EOrderStatus.PENDING)
                .paymentStatus(EOrderPaymentStatus.UNPAID)
                .orderAmt(request.getTotal())
                .orderItems(cart.getCartItems())
                .build();
    }

}
