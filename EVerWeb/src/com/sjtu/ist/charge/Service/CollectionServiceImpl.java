package com.sjtu.ist.charge.Service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sjtu.ist.charge.Dao.CollectionDao;
import com.sjtu.ist.charge.Model.Collection;


@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {

	private CollectionDao collectionDao;

	public CollectionDao getCollectionDao() {
		return collectionDao;
	}

	@Resource(name="collectionDao")
	public void setCollectionDao(CollectionDao collectionDao) {
		this.collectionDao = collectionDao;
	}

	public List<Collection> getColByUId(int uid) {
		return collectionDao.getColByUId(uid);
	}

	public boolean addCollection(Collection collection) {
		return collectionDao.add(collection);
	}

	public boolean deleteCollection(Collection collection) {
		return collectionDao.delete(collection);
	}

	public Collection load(int id) {
		return collectionDao.load(id);
	}

	public Collection getByUserAndStakeId(int user_id, int stake_id) {
		return collectionDao.getColByUserIdAndStakeId(user_id, stake_id);
	}
}
