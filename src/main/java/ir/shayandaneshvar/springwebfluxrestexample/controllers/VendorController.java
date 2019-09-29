package ir.shayandaneshvar.springwebfluxrestexample.controllers;

import ir.shayandaneshvar.springwebfluxrestexample.domain.Category;
import ir.shayandaneshvar.springwebfluxrestexample.domain.Vendor;
import ir.shayandaneshvar.springwebfluxrestexample.repositories.CategoryRepository;
import ir.shayandaneshvar.springwebfluxrestexample.repositories.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
@RestController
public class VendorController {
    private final VendorRepository vendorRepository;

    @GetMapping("/api/v1/vendors")
    Flux<Vendor> list() {
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    Mono<Vendor> getById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }
}
