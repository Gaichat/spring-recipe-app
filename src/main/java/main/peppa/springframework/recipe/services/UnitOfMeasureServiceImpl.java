package main.peppa.springframework.recipe.services;

import main.peppa.springframework.recipe.commands.UnitOfMeasureCommand;
import main.peppa.springframework.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import main.peppa.springframework.recipe.model.UnitOfMeasure;
import main.peppa.springframework.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository uomRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand converter) {
        this.uomRepository = uomRepository;
        this.converter = converter;
    }

    @Override
    public Set<UnitOfMeasureCommand> findAll() {
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        uomRepository.findAll().forEach(unitOfMeasures::add);
        return unitOfMeasures.stream().map(converter::convert).collect(Collectors.toSet());
    }
}
