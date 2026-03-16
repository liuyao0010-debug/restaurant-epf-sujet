package fr.epf.restaurant.service;

import fr.epf.restaurant.dao.PlatDao;
import fr.epf.restaurant.dto.PlatDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatService {
    private final PlatDao dao;
    public PlatService(PlatDao dao){this.dao=dao;}
    public List<PlatDTO> getAllPlats(){return dao.findAll();}
    public PlatDTO getPlatById(Long id){return dao.findById(id);}
}