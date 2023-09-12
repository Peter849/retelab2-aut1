package hu.bme.aut.retelab2.repository;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.util.SecretGenerator;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AdRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Ad save(Ad advertisement) {
        advertisement.setSecretCode(SecretGenerator.generate());
        return em.merge(advertisement);
    }

    public List<Ad> findByPrice(int minimum, int maximum) {
        List<Ad> result = em.createQuery("select a from Ad a where a.price between ?1 and ?2", Ad.class)
                .setParameter(1, minimum)
                .setParameter(2, maximum)
                .getResultList();

        for (Ad ad : result) {
            ad.setSecretCode(null);
        }

        return result;
    }

    public List<Ad> findBytag(String tag){
        List<Ad> result = em.createQuery("select a from Ad a where ?1 member a.tags", Ad.class)
                .setParameter(1, tag)
                .getResultList();

        for (Ad ad : result) {
            ad.setSecretCode(null);
        }

        return result;
    }

    @Transactional
    public Ad checkCodeAndSave(Ad modifiedAdvertisement){
        if(modifiedAdvertisement == null)
        {
            return null;
        }

        Ad requestedAd = em.find(Ad.class, modifiedAdvertisement.getId());

        if(!modifiedAdvertisement.getSecretCode().equals(requestedAd.getSecretCode()))
        {
            return null;
        }

        save(modifiedAdvertisement);

        return modifiedAdvertisement;
    }

    @Scheduled(fixedDelay= 6000)
    @Transactional
    public void deleteExpiredEntities()
    {
        LocalDateTime currentTime = LocalDateTime.now();
        List<Ad> resultList = em.createQuery("select a from Ad a where a.expirationDate < ?1", Ad.class)
                .setParameter(1, currentTime)
                .getResultList();

        if(!resultList.isEmpty()){
            for (Ad ad : resultList) {
                String adTitle = ad.getTitle();
                em.remove(ad);
                System.out.println("Advertisement with title: " + adTitle + " have been successfully removed at: " + LocalDateTime.now());
            }
        }
    }
}
