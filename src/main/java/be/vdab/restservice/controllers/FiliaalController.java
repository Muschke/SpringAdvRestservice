package be.vdab.restservice.controllers;

import be.vdab.restservice.domain.Filiaal;
import be.vdab.restservice.exceptions.FiliaalNietGevondenException;
import be.vdab.restservice.services.FiliaalService;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.TypedEntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/filialen")
@CrossOrigin(exposedHeaders = "Location")
//@ExposesResourceFor(Filiaal.class)
class FiliaalController {
    private final FiliaalService filiaalService;
    //private final TypedEntityLinks.ExtendedTypedEntityLinks<Filiaal> links;

    public FiliaalController(FiliaalService filiaalService/*, EntityLinks links*/) {

        this.filiaalService = filiaalService;
       // this.links = links.forType(Filiaal.class, Filiaal::getId);
    }
    @GetMapping
    List<Filiaal> getAlleFilialen() {
        return filiaalService.findAll();
    }

   // @GetMapping("{id}")
    //Filiaal get(@PathVariable long id) {
     //   return filiaalService.findById(id)
      //          .orElseThrow(FiliaalNietGevondenException::new);
    //}

    /*om response te sturen bij 404*/
    //@ExceptionHandler(FiliaalNietGevondenException.class)
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    //void filiaalNietGevonden() {
    //}
    /*verwijderen*/
    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        filiaalService.delete(id);
    }

    /*toevoegen*/
    @PostMapping
    void post(@RequestBody @Valid Filiaal filiaal) {
        filiaalService.create(filiaal);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String,String> verkeerdeData(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField,
                        FieldError::getDefaultMessage));
    }

/*om te updaten, hiervoor moet je dus zorgen dat je in je object een methode maakt die het object kopieert met de parameter id
* die parameter krijgen we door van de browser*/
    @PutMapping("{id}")
    void put(@PathVariable long id,
             @RequestBody @Valid Filiaal filiaal) {
        filiaalService.update(filiaal.withId(id));
    }


    /*getmapping maken voor een detailpagina*/
    @GetMapping("/frontendRestservice/detail/{id}")
    Filiaal get(@PathVariable long id) {
        return filiaalService.findById(id)
                .orElseThrow(FiliaalNietGevondenException::new);
    }
    @ExceptionHandler(FiliaalNietGevondenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void filiaalNietGevonden() {
    }
}
