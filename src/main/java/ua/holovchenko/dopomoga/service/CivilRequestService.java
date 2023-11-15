package ua.holovchenko.dopomoga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.holovchenko.dopomoga.entity.CivilRequest;
import ua.holovchenko.dopomoga.repository.CivilRequestRepository;
import ua.holovchenko.dopomoga.repository.criteria.CivilRequestSpecification;
import ua.holovchenko.dopomoga.repository.criteria.DateFilter;
import ua.holovchenko.dopomoga.repository.criteria.Filter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CivilRequestService {

    private final CivilRequestRepository repo;
    private final CivilRequestSpecification spec;

    @Autowired
    public CivilRequestService(CivilRequestRepository repo, CivilRequestSpecification spec) {
        this.repo = repo;
        this.spec = spec;
    }

    public Page<CivilRequest> getCivilRequestPage(List<Filter> filterList,
                                                  List<DateFilter> dateLessThanList,
                                                  List<DateFilter> dateGreaterThanList,
                                                  Integer page,
                                                  Integer size) {
        if (page == null || page < 1)
            page = 1;

        if (size == null || size < 1)
            size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);
        return repo.findAll(spec.columnEqual(filterList)
                .and(spec.dateLessThan(dateLessThanList))
                .and(spec.dateGreaterThan(dateGreaterThanList)), pageable);
    }

    public CivilRequest getCivilRequestByID(String id) throws NoSuchElementException {
        return repo.findById(UUID.fromString(id)).orElseThrow();
    }

    public CivilRequest saveCivilRequest(CivilRequest civilRequest) {
        return repo.save(civilRequest);
    }
}
