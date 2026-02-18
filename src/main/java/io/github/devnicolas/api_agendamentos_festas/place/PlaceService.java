package io.github.devnicolas.api_agendamentos_festas.place;

import io.github.devnicolas.api_agendamentos_festas.interfaces.services.BaseServiceImpl;
import io.github.devnicolas.api_agendamentos_festas.place.dtos.PlaceRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class PlaceService extends BaseServiceImpl<Place, PlaceRequestDTO, Long> {


  public PlaceService(PlaceRepository placeRepository) {
    super(placeRepository);
  }


  @Override
  protected Place toEntity(PlaceRequestDTO dto) {
    return new Place(dto.name(), dto.capacity(), dto.address());
  }

  @Override
  protected void updateEntity(Place entity, PlaceRequestDTO dto) {

  }

}
