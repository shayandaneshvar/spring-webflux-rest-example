package ir.shayandaneshvar.springwebfluxrestexample.controllers;

import ir.shayandaneshvar.springwebfluxrestexample.domain.Category;
import ir.shayandaneshvar.springwebfluxrestexample.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


public class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @Before
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void list() {
        BDDMockito.given(categoryRepository.findAll()).willReturn(Flux.just
                (Category.builder().description("Cat1").build(), Category.builder().description("Cat2").build()));
        webTestClient.get().uri("/api/v1/categories").exchange().expectBodyList(Category.class).hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(categoryRepository.findById("someId")).willReturn(Mono.just(
                Category.builder().description("Cat").build()));

        webTestClient.get().uri("/api/v1/categories/someid").exchange().expectBody(Category.class);

    }

    @Test
    public void testCreateCategory() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class))).willReturn(Flux.just(Category.builder().build()));
        Mono<Category> catToSave =
                Mono.just(Category.builder().description("Some Cat").build());
        webTestClient.post().uri("/api/v1/categories").body(catToSave, Category.class)
                .exchange().expectStatus().isCreated();
    }

    @Test
    public void testUpdateCategory() {
        BDDMockito.given(categoryRepository.save(any(Category.class))).willReturn(Mono.
                just(Category.builder().build()));

        Mono<Category> update = Mono.just(Category.builder().description("sth").build());
        webTestClient.put().uri("/api/v1/categories/sth").body(update,
                Category.class).exchange().expectStatus().isOk();

    }

    @Test
    public void testPatchCategory() {
        BDDMockito.given(categoryRepository.findById(anyString())).willReturn(Mono.just(Category.builder().build()));
        BDDMockito.given(categoryRepository.save(any(Category.class))).willReturn(Mono.
                just(Category.builder().build()));

        Mono<Category> update = Mono.just(Category.builder().description("sth").build());
        webTestClient.patch().uri("/api/v1/categories/sth").body(update,
                Category.class).exchange().expectStatus().isOk();
        BDDMockito.verify(categoryRepository).save(any());
    }

    @Test
    public void testPatchCategoryNoChanges() {
        BDDMockito.given(categoryRepository.findById(anyString())).willReturn(Mono.just(Category.builder().build()));
        BDDMockito.given(categoryRepository.save(any(Category.class))).willReturn(Mono.
                just(Category.builder().build()));

        Mono<Category> update = Mono.just(Category.builder().build());
        webTestClient.patch().uri("/api/v1/categories/sth").body(update,
                Category.class).exchange().expectStatus().isOk();
        BDDMockito.verify(categoryRepository,Mockito.never()).save(any());
    }

}
