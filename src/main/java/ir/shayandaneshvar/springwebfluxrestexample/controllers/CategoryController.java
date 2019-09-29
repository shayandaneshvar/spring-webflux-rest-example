package ir.shayandaneshvar.springwebfluxrestexample.controllers;

import ir.shayandaneshvar.springwebfluxrestexample.domain.Category;
import ir.shayandaneshvar.springwebfluxrestexample.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping("/api/v1/categories")
    Flux<Category> list() {
        return categoryRepository.findAll();
    }

    @GetMapping("/api/v1/categories/{id}")
    Mono<Category> getById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @PostMapping("/api/v1/categories")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> create(@RequestBody Publisher<Category> categoryStream) {
        return categoryRepository.saveAll(categoryStream).then();
    }

    @PutMapping("/api/v1/categories/{id}")
    Mono<Category> update(@PathVariable String id, @RequestBody Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PatchMapping("/api/v1/categories/{id}")
    Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {
        Category found = categoryRepository.findById(id).block();
        if (found.getDescription() != category.getDescription()) {
            found.setDescription(category.getDescription());
            return categoryRepository.save(found);
        }
        return Mono.just(found);
    }

}
