package ir.shayandaneshvar.springwebfluxrestexample.controllers;

import ir.shayandaneshvar.springwebfluxrestexample.domain.Vendor;
import ir.shayandaneshvar.springwebfluxrestexample.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController controller;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        controller = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    public void list() {
        BDDMockito.given(vendorRepository.findAll()).willReturn(Flux.just(Vendor.builder()
                        .firstName("Fred").lastName("Mercury").build(),
                Vendor.builder().firstName("barney").lastName("Stinson").build()));
        webTestClient.get().uri("/api/v1/vendors").exchange().expectBodyList(Vendor.
                class).hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(vendorRepository.findById("someid")).willReturn(Mono.just(Vendor
                .builder().firstName("jim").lastName("kazama").build()));
        webTestClient.get().uri("/api/v1/vendors/someid").exchange().expectBody(
                Vendor.class);
    }
}