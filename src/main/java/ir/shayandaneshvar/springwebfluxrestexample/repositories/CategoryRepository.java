package ir.shayandaneshvar.springwebfluxrestexample.repositories;

import ir.shayandaneshvar.springwebfluxrestexample.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
