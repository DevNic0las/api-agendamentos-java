package io.github.devnicolas.api_agendamentos_festas.place;

import io.github.devnicolas.api_agendamentos_festas.interfaces.services.controllers.BaseControllerImpl;
import io.github.devnicolas.api_agendamentos_festas.place.dtos.PlaceRequestDTO;
import io.github.devnicolas.api_agendamentos_festas.place.dtos.PlaceResponseDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/place")
public class PlaceController extends BaseControllerImpl<Place, PlaceRequestDTO, PlaceResponseDTO, Long> {

  public PlaceController(PlaceService placeService) {
    super(placeService);
  }

  @Override
  protected PlaceResponseDTO toResponseDTO(Place place) {
    return new PlaceResponseDTO(
      place.getName(),
      place.getCapacity(),
      place.getAddress(),
      place.getId());
  }
}
