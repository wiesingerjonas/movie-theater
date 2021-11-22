package at.htl.movietheater.control;

import at.htl.movietheater.entity.Show;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class ShowRepository {

    @Inject
    EntityManager em;

    public Show save(Show show) {

        if(show.getPrevShow() == null) {
            show.setPrevShow(findLastShow());
        }

        show = em.merge(show);
        if(show.getPrevShow() != null) {
            show.getPrevShow().setNextShow(show);
        }

        return show;
    }

    /**
     * use a NamedQuery "Show.findLastShow" to retrieve the last show stored in the db
     * when the NoResultException is thrown, return null
     *
     * @return the last show stored in the db
     */
    public Show findLastShow() {
        try {
            TypedQuery<Show> showTypedQuery = em
                    .createNamedQuery("Show.findLastShow", Show.class)
                    .setMaxResults(1);
            return showTypedQuery.getSingleResult();
        } catch (NoResultException e){
            return null;
        }

    }

    /**
     * use a built-in method provided by the entity manger to find a show by its id
     *
     * @param id
     * @return the retrieved show
     */
    public Show findById(long id) {
        return em.find(Show.class,id);
    }
}
