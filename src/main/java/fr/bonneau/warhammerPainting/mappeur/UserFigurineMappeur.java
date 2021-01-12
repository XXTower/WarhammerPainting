package fr.bonneau.warhammerPainting.mappeur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import fr.bonneau.warhammerPainting.dto.UserFigurineDto;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.UserFigurine;

@Component
public class UserFigurineMappeur {
	private FigurineMappeur figurineMappeur;
	private UserMappeur userMappeur;
	private PaintingMappeur paintingMappeur;

	public UserFigurineMappeur(FigurineMappeur figurineMappeur, UserMappeur userMappeur, PaintingMappeur paintingMappeur) {
		this.figurineMappeur = figurineMappeur;
		this.userMappeur = userMappeur;
		this.paintingMappeur = paintingMappeur;
	}

	public UserFigurine dtoToUserFigurine(UserFigurineDto dto) {
		if(dto == null) {
			return null;
		}
		UserFigurine userFigurine = new UserFigurine();
		userFigurine.setId(dto.getId());
		userFigurine.setTitle(dto.getTitle());
		userFigurine.setDescription(dto.getDescription());
		userFigurine.setVisibility(dto.isVisibility());
		userFigurine.setFigurine(figurineMappeur.mapToFigurine(dto.getFigurine()));
		userFigurine.setUser(userMappeur.mapToUser(dto.getUser()));
		userFigurine.setListPainting(new HashSet<Painting>(paintingMappeur.dtosToPaintings(dto.getListPainting())));
		return userFigurine;
	}
	
	public List<UserFigurine> dtosToUserFigurines(List<UserFigurineDto> dtos) {
		if (dtos == null) {
			return new ArrayList<UserFigurine>();
		}
		return dtos.stream()
				.filter(Objects::nonNull)
				.map(dto -> dtoToUserFigurine(dto))
				.collect(Collectors.toList());
	}

	public UserFigurineDto userFigurineToDto(UserFigurine userFigurine) {
		if(userFigurine == null) {
			return null;
		}
		UserFigurineDto dto = new UserFigurineDto();
		dto.setId(userFigurine.getId());
		dto.setTitle(userFigurine.getTitle());
		dto.setDescription(userFigurine.getDescription());
		dto.setVisibility(userFigurine.isVisibility());
		dto.setFigurine(figurineMappeur.mapToDto(userFigurine.getFigurine()));
		dto.setUser(userMappeur.mapToDto(userFigurine.getUser()));
		dto.setListPainting(paintingMappeur.paintingsToDtos(new ArrayList<Painting>(userFigurine.getListPainting())));
		return dto;
	}
	
	public List<UserFigurineDto> userFigurinesToDtos(List<UserFigurine> userFigurines) {
		if (userFigurines == null) {
			return new ArrayList<UserFigurineDto>();
		}
		return userFigurines.stream()
				.filter(Objects::nonNull)
				.map(userFigurine -> userFigurineToDto(userFigurine))
				.collect(Collectors.toList());
	}

}
