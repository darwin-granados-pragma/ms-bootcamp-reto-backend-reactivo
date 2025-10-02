package co.com.bootcamp.consumer.mapper;

import co.com.bootcamp.consumer.model.CapacityRestResponse;
import co.com.bootcamp.model.capacity.CapacityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CapacityMapper {

  CapacityResponse toDomain(CapacityRestResponse response);
}
