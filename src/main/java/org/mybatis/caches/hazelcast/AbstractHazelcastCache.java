/**
 *    Copyright 2010-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.caches.hazelcast;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;

/**
 * @author Simone Tripodi
 */
public abstract class AbstractHazelcastCache implements Cache {

    /**
     * The {@code ReadWriteLock}.
     */
    protected final ReadWriteLock readWriteLock = new DummyReadWriteLock();

    /**
     * The cache id.
     */
    protected final String id;

    /**
     * The cache map reference.
     */
    protected final Map<Object, Object> cacheMap;

    protected AbstractHazelcastCache(String id, Map<Object, Object> iMap) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an id");
        }
        if (iMap == null) {
            throw new IllegalArgumentException("Cache instances require a cacheMap");
        }
        this.id = id;
        this.cacheMap = iMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        this.cacheMap.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getObject(Object key) {
        return this.cacheMap.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return this.cacheMap.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putObject(Object key, Object value) {
    	if(value!=null) {
    		cacheMap.put(key, value);
    	}
    	else {
    		if(cacheMap.containsKey(key)) {
    			cacheMap.remove(key);
    		}
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object removeObject(Object key) {
        return this.cacheMap.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Cache)) {
            return false;
        }

        Cache otherCache = (Cache) obj;
        return this.id.equals(otherCache.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Hazelcast {" + this.id + "}";
    }

}
