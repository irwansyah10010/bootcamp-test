package com.lawencon.bootcamptest.business.user.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.lawencon.bootcamptest.base.BaseRepository;
import com.lawencon.bootcamptest.business.file.model.File;
import com.lawencon.bootcamptest.business.role.model.Role;
import com.lawencon.bootcamptest.business.user.dto.detail.UserResponse;
import com.lawencon.bootcamptest.business.user.dto.list.UsersResponse;
import com.lawencon.bootcamptest.business.user.model.StatusUser;
import com.lawencon.bootcamptest.business.user.model.User;

@Repository
public class UserDao extends BaseRepository{
    
    public Optional<User> loadUsername(String username){
        Query nativeQuery = this.getConnection()
                    .createNativeQuery("SELECT * FROM tb_user where username = :username", User.class)
                    .setParameter("username", username);

        User singleResult = (User) nativeQuery.getSingleResult();

        return Optional.ofNullable(singleResult);
    }

    /**
     * 
     * @param username
     * @return
     * @throws NoResultException
     */
    public Optional<UserResponse> getByUsername(String username) throws NoResultException {
        CriteriaBuilder criteriaBuilder = getConnection().getCriteriaBuilder();

        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<User> user = criteriaQuery.from(User.class);
        Join<User,Role> role = user.join("role", JoinType.INNER);
        Join<User,File> profilePicture = user.join("profilePicture", JoinType.LEFT);
        Join<User,StatusUser> status = user.join("statusUser", JoinType.LEFT);

        criteriaQuery.where(criteriaBuilder.equal(user.get("username"), username));

        criteriaQuery.select(criteriaBuilder.array(
            user.get("username"),
            user.get("fullName"),
            role.get("roleCode"),
            role.get("roleName"),
            user.get("placeOfBirth"),
            user.get("email"),
            profilePicture.get("file"),
            status.get("enabled"),
            status.get("isLoginWeb"),

            user.get("dateOfBirth"),
            user.get("phoneNumber"),
            user.get("address"),

            status.get("id")

        ));

        TypedQuery<Object[]> query = getConnection().createQuery(criteriaQuery);

        try{
            Object[] obj = query.getSingleResult();   
            UserResponse usr = new UserResponse();

            usr.setUsername((String) obj[0]);
            usr.setFullName((String) obj[1]);
            usr.setRole((String) obj[2] +" - "+(String) obj[3]);
            usr.setPlaceOfBirth((String) obj[4]);
            usr.setEmail((String) obj[5]);

            usr.setProfile(Optional.ofNullable((String) obj[6]).orElse(null));

            usr.setEnabled(Optional.ofNullable((Boolean) obj[7]).orElse(false));
            usr.setIsLoginWeb(Optional.ofNullable((Boolean) obj[8]).orElse(false));

            usr.setDateOfBirth((LocalDate) obj[9]);
            usr.setPhoneNumber(Optional.ofNullable((String) obj[10]).orElse(null));
            usr.setAddress(Optional.ofNullable((String) obj[11]).orElse(null));

            usr.setStatusId(Optional.ofNullable((String) obj[12]).orElse(null));
    
            return Optional.of(usr);

        } catch(NoResultException e){
            throw new NoResultException("User tidak ditemukan");
        }
    }


    /**
     * 
     * @param username
     * @return
     */
    private TypedQuery<Object> queryOfGetAll(String username){
        CriteriaBuilder criteriaBuilder = getConnection().getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();

        Root<User> user = criteriaQuery.from(User.class);
        Join<User,Role> role = user.join("role", JoinType.INNER);
        Join<User,File> profilePicture = user.join("profilePicture", JoinType.LEFT);
        Join<User,StatusUser> status = user.join("statusUser", JoinType.LEFT);

        Predicate isActive = criteriaBuilder.equal(user.get("isActive"), true);

        if(Optional.ofNullable(username).isPresent()){
            criteriaQuery.where(criteriaBuilder.like(user.get("username"), username+"%"), isActive);
        }

        criteriaQuery.where(isActive);

        criteriaQuery.select(criteriaBuilder.array(
            user.get("username"),
            user.get("fullName"),
            role.get("roleCode"),
            role.get("roleName"),
            user.get("placeOfBirth"),
            user.get("email"),
            profilePicture.get("file"),
            status.get("enabled"),
            status.get("isLoginWeb")
        ));

        return getConnection().createQuery(criteriaQuery);
    }

    public List<UsersResponse> getAll(){
        TypedQuery<Object> query = queryOfGetAll(null);

        List<Object> users = query.getResultList();

        List<UsersResponse> usersResponses = new ArrayList<>();
        users.forEach(usr -> {

            UsersResponse usersResponse = new UsersResponse();

            Object[] u = (Object[]) usr;
            
            usersResponse.setUsername((String) u[0]);
            usersResponse.setFullName((String) u[1]);
            usersResponse.setRole((String) u[2] +" - "+(String) u[3]);
            usersResponse.setPlaceOfBirth((String) u[4]);
            usersResponse.setEmail((String) u[5]);

            usersResponse.setProfile(Optional.ofNullable((String) u[6]).orElse(null));

            usersResponse.setEnabled(Optional.ofNullable((Boolean) u[7]).orElse(false));
            usersResponse.setIsLoginWeb(Optional.ofNullable((Boolean) u[8]).orElse(false));

            usersResponses.add(usersResponse);
        });

        return usersResponses;
    }

    public List<UsersResponse> getAll(Integer page, Integer limit){
        
        Integer start = limit * (page - 1);
        Integer last = start + limit;

        TypedQuery<Object> query = queryOfGetAll(null)
                                        .setFirstResult(start)
                                        .setMaxResults(last);

        List<Object> users = query.getResultList();

        List<UsersResponse> usersResponses = new ArrayList<>();
        users.forEach(usr -> {

            UsersResponse usersResponse = new UsersResponse();

            Object[] u = (Object[]) usr;
            
            usersResponse.setUsername((String) u[0]);
            usersResponse.setFullName((String) u[1]);
            usersResponse.setRole((String) u[2] +" - "+(String) u[3]);
            usersResponse.setPlaceOfBirth((String) u[4]);
            usersResponse.setEmail((String) u[5]);

            usersResponse.setProfile(Optional.ofNullable((String) u[6]).orElse(null));

            usersResponse.setEnabled(Optional.ofNullable((Boolean) u[7]).orElse(false));
            usersResponse.setIsLoginWeb(Optional.ofNullable((Boolean) u[8]).orElse(false));

            usersResponses.add(usersResponse);
        });

        return usersResponses;
    }

    public List<UsersResponse> getAll(String username, Integer page, Integer limit){
        Integer start = limit * (page - 1);
        Integer last = start + limit;

        TypedQuery<Object> query = queryOfGetAll(username)
                                        .setFirstResult(start)
                                        .setMaxResults(last);

        List<Object> users = query.getResultList();

        List<UsersResponse> usersResponses = new ArrayList<>();
        users.forEach(usr -> {
            UsersResponse usersResponse = new UsersResponse();

            Object[] u = (Object[]) usr;
            
            usersResponse.setUsername((String) u[0]);
            usersResponse.setFullName((String) u[1]);
            usersResponse.setRole((String) u[2] +" - "+(String) u[3]);
            usersResponse.setPlaceOfBirth((String) u[4]);
            usersResponse.setEmail((String) u[5]);

            usersResponse.setProfile(Optional.ofNullable((String) u[6]).orElse(null));

            usersResponse.setEnabled(Optional.ofNullable((Boolean) u[7]).orElse(false));
            usersResponse.setIsLoginWeb(Optional.ofNullable((Boolean) u[8]).orElse(false));

            usersResponses.add(usersResponse);
        });

        return usersResponses;
    }

    public Boolean isSoftDelete(String username){
        User userById = getById(User.class, username);
        Boolean isActive = userById.getIsActive();

        User userUpdate=null;
        if(Optional.ofNullable(userById).isPresent()){
            if(isActive)
                userById.setIsActive(false);

            userUpdate = update(userById);
        }

        return Optional.ofNullable(userUpdate).isPresent();

    }
}
