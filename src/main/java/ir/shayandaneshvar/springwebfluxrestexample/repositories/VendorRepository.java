package ir.shayandaneshvar.springwebfluxrestexample.repositories;

import ir.shayandaneshvar.springwebfluxrestexample.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
