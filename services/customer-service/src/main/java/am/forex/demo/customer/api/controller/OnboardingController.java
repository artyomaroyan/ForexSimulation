package am.forex.demo.customer.api.controller;

import am.forex.demo.customer.api.dto.CustomerRequest;
import am.forex.demo.customer.api.dto.CustomerResponse;
import am.forex.demo.customer.service.usecase.OnboardingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 10:59:33
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class OnboardingController {
    private final OnboardingUseCase customerService;

    @PostMapping("/create")
    ResponseEntity<Mono<CustomerResponse>> createNewCustomer(@RequestBody CustomerRequest request){
        var result = customerService.createNewCustomer(request);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }
}