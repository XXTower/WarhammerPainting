package fr.bonneau.warhammerPainting.mappeur;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.bonneau.warhammerPainting.dto.FigurineDto;
import fr.bonneau.warhammerPainting.dto.PaintingDto;
import fr.bonneau.warhammerPainting.dto.UserDto;
import fr.bonneau.warhammerPainting.dto.UserFigurineDto;
import fr.bonneau.warhammerPainting.models.Figurine;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.User;
import fr.bonneau.warhammerPainting.models.UserFigurine;
import fr.bonneau.warhammerPainting.models.enums.Faction;
import fr.bonneau.warhammerPainting.models.enums.PaintingTypes;
import fr.bonneau.warhammerPainting.models.enums.SubFaction;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserFigurineMappeurTest {

	private UserFigurineMappeur mapper;
	private UserFigurine userFigurine;
	private UserFigurineDto dto;
	private FigurineMappeur figurineMappeur;
	private UserMappeur userMappeur;
	private PaintingMappeur paintingMappeur;
	private Painting painting;
	private PaintingDto paintingDto;
	private User user;
	private UserDto userDto;
	private Figurine figurine;
	private FigurineDto figurineDto;
	private List<Painting> paintingsList;
	private List<PaintingDto> paintingDtos;
	
	@BeforeEach
	public void beforEach() {
		
		painting = new Painting();
		painting.setId(1);
		painting.setName("abadon black");
		painting.setType(PaintingTypes.BASE);

		paintingDto = new PaintingDto();
		paintingDto.setId(1);
		paintingDto.setName("abadon black");
		paintingDto.setType(PaintingTypes.BASE);
		
		Set<Painting> paintingsSet = Collections.singleton(painting);
		paintingsList = Collections.singletonList(painting);
		paintingDtos = Collections.singletonList(paintingDto);
		
		user = new User();
		user.setId(1);
        user.setUsername("test");
        user.setPassword("test");

        userDto = new UserDto();
        userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("test");
        userDto.setPassword("test");
        
        figurine = new Figurine();
        figurine.setId(1);
        figurine.setName("test");
        figurine.setFaction(Faction.SPACE_MARINS);
        figurine.setSubFaction(SubFaction.ULTRAMARINS);
        
        figurineDto = new FigurineDto();
        figurineDto.setId(1);
        figurineDto.setName("test");
        figurineDto.setFaction(Faction.SPACE_MARINS);
        figurineDto.setSubFaction(SubFaction.ULTRAMARINS);
        
        userFigurine = new UserFigurine();
        userFigurine.setId(1);
		userFigurine.setTitle("title");
		userFigurine.setDescripsion("Descripsion");
		userFigurine.setVisibility(true);
		userFigurine.setFigurine(figurine);
		userFigurine.setUser(user);
		userFigurine.setListPainting(paintingsSet);
		
		dto = new UserFigurineDto();
		dto.setId(1);
		dto.setTitle("title");
		dto.setDescription("Descripsion");
		dto.setVisibility(true);
		dto.setFigurine(figurineDto);
		dto.setUser(userDto);
		dto.setListPainting(paintingDtos);
		
		figurineMappeur = mock(FigurineMappeur.class);
		userMappeur = mock(UserMappeur.class);
		paintingMappeur = mock(PaintingMappeur.class);
		mapper = new UserFigurineMappeur(figurineMappeur, userMappeur, paintingMappeur);
		
		
	}

	//   ------userFigurineToDto------

	@Test
	public void userFigurineToDtoTest() {
		when(figurineMappeur.mapToDto(figurine)).thenReturn(figurineDto);
		when(userMappeur.mapToDto(user)).thenReturn(userDto);
		when(paintingMappeur.paintingsToDtos(paintingsList)).thenReturn(paintingDtos);
		UserFigurineDto dtoTest = mapper.userFigurineToDto(userFigurine);
		assertEquals(dtoTest,dto);
	}
	
	@Test
	public void userFigurineToDtoTestNull() {
		UserFigurineDto dtoTest = mapper.userFigurineToDto(null);
		assertNull(dtoTest);
	}

	//   ------dtoToUserFigurine------

	@Test
	public void dtoToUserFigurineTest() {
		when(figurineMappeur.mapToFigurine(figurineDto)).thenReturn(figurine);
		when(userMappeur.mapToUser(userDto)).thenReturn(user);
		when(paintingMappeur.dtosToPaintings(paintingDtos)).thenReturn(paintingsList);
		UserFigurine userFigurineTest = mapper.dtoToUserFigurine(dto);
		assertEquals(userFigurineTest,userFigurine);
	}
	
	@Test
	public void dtoToUserFigurineTestNull() {
		UserFigurine userFigurineTest = mapper.dtoToUserFigurine(null);
		assertNull(userFigurineTest);
	}

	//  ------userFigurinesToDtos------

	@Test
	public void userFigurinesToDtosTest() {
		when(figurineMappeur.mapToDto(figurine)).thenReturn(figurineDto);
		when(userMappeur.mapToDto(user)).thenReturn(userDto);
		when(paintingMappeur.paintingsToDtos(paintingsList)).thenReturn(paintingDtos);
		List<UserFigurine> userFigurines = Collections.singletonList(userFigurine);
		List<UserFigurineDto> dtos = Collections.singletonList(dto);
		List<UserFigurineDto> dtosTest = mapper.userFigurinesToDtos(userFigurines);
		assertEquals(dtos, dtosTest);
	}
	
	@Test
	public void userFigurinesToDtosTestEmptyList() {
		List<UserFigurineDto> dtosTest = mapper.userFigurinesToDtos(new ArrayList<UserFigurine>());
		assertThat(dtosTest).isEmpty();
	}
	
	@Test
	public void userFigurinesToDtosTestNull() {
		List<UserFigurineDto> dtosTest = mapper.userFigurinesToDtos(null);
		assertThat(dtosTest).isEmpty();
	}

	//  ------dtosToUserFigurines------
	
	@Test
	public void dtosToUserFigurinesTest() {
		when(figurineMappeur.mapToFigurine(figurineDto)).thenReturn(figurine);
		when(userMappeur.mapToUser(userDto)).thenReturn(user);
		when(paintingMappeur.dtosToPaintings(paintingDtos)).thenReturn(paintingsList);
		List<UserFigurine> userFigurines = Collections.singletonList(userFigurine);
		List<UserFigurineDto> dtos = Collections.singletonList(dto);
		List<UserFigurine> userFigurinesTest = mapper.dtosToUserFigurines(dtos);
		assertEquals(userFigurines, userFigurinesTest);
	}
	
	@Test
	public void dtosToUserFigurinesTestEmptyList() {
		List<UserFigurine> userFigurinesTest = mapper.dtosToUserFigurines(new ArrayList<UserFigurineDto>());
		assertThat(userFigurinesTest).isEmpty();
	}
	
	@Test
	public void dtosToUserFigurinesTestNull() {
		List<UserFigurine> userFigurinesTest = mapper.dtosToUserFigurines(null);
		assertThat(userFigurinesTest).isEmpty();
	}
}
