package hva.nl.api.repositories;

import hva.nl.api.models.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class MapRepository {

    @Autowired
    private EntityManager em;

    public List<Map> getMaps() {
        return em.createNamedQuery("find_all_maps", Map.class).getResultList();
    }

    public Map setMap(String key, int value){
        return em.merge(new Map(key, value));
    }

    public Map setMap(Map map){
        return em.merge(map);
    }

    public Map getMapByKey(String key){
        return getMaps()
                .stream()
                .filter(m ->
                        (m.getKeyname().equals(key)))
                .findFirst()
                .orElse(null);
    }
}
