package co.com.bootcamp.api.mapper;

import co.com.bootcamp.api.model.request.BootcampCreateRequest;
import co.com.bootcamp.api.model.response.BootcampResponse;
import co.com.bootcamp.model.bootcamp.Bootcamp;
import co.com.bootcamp.model.bootcamp.BootcampCreate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface BootcampRestMapper {

  BootcampCreate toBootcampCreate(BootcampCreateRequest request);

  BootcampResponse toBootcampResponse(Bootcamp bootcamp);
}
