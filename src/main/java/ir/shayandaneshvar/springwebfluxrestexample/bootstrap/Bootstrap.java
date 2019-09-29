package ir.shayandaneshvar.springwebfluxrestexample.bootstrap;

import ir.shayandaneshvar.springwebfluxrestexample.domain.Category;
import ir.shayandaneshvar.springwebfluxrestexample.domain.Vendor;
import ir.shayandaneshvar.springwebfluxrestexample.repositories.CategoryRepository;
import ir.shayandaneshvar.springwebfluxrestexample.repositories.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count().block() == 0) {
            //load
            System.out.println("Loading Data");
            categoryRepository.save(Category.builder().description("Fruits").build()).block();
            categoryRepository.save(Category.builder().description("Eggs").build()).block();
            categoryRepository.save(Category.builder().description("Meats").build()).block();
            categoryRepository.save(Category.builder().description("Breads").build()).block();
            categoryRepository.save(Category.builder().description("Nuts").build()).block();
            System.out.println("loaded Categories: " + categoryRepository.count().block());

            vendorRepository.save(Vendor.builder().firstName("joe").lastName("buck").build()).block();
            vendorRepository.save(Vendor.builder().firstName("jessie").lastName("waters").build()).block();
            vendorRepository.save(Vendor.builder().firstName("jim").lastName("karter").build()).block();
            System.out.println("loaded Categories: " + vendorRepository.count().block());
        }
    }
}
