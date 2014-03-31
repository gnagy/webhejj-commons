/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.beans;

import hu.webhejj.commons.collections.ArrayUtils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * <p>An enhanced version of java.beans.PropertyChangeSupport that
 * adds the same listener only once, and does not fire the property
 * changed event if there are no listeners.</p>
 */
public class PropertyChangeSupport extends java.beans.PropertyChangeSupport {

	private static final long serialVersionUID = 2248004830395804326L;

	public PropertyChangeSupport(Object bean) {
		super(bean);
	}

	/** add listener if not already added */
    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
    	if(!ArrayUtils.contains(listener, getPropertyChangeListeners())) {
			super.addPropertyChangeListener(listener);
		}
    }
    
    /** add listener if not already added to property */
    public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    	if(!ArrayUtils.contains(listener, getPropertyChangeListeners(propertyName))) {
			super.addPropertyChangeListener(propertyName, listener);
		}
    }
    
    /** fire event if there are any listeners for it */
    public void firePropertyChange(PropertyChangeEvent event) {
    	if(hasListeners(event.getPropertyName())) {
    		super.firePropertyChange(event);
    	}
    }

    /** fire event if there are any listeners on the property */
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    	if(hasListeners(propertyName)) {
    		super.firePropertyChange(propertyName, oldValue, newValue);
    	}
    }
}
