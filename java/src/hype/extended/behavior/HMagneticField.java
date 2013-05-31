/*
 * HYPE_Processing
 * http://www.hypeframework.org/ & https://github.com/hype/HYPE_Processing
 * 
 * Copyright (c) 2013 Joshua Davis & James Cruz
 * 
 * Distributed under the BSD License. See LICENSE.txt for details.
 * 
 * All rights reserved.
 */

package hype.extended.behavior;

import hype.core.behavior.HBehavior;
import hype.core.collection.HIterator;
import hype.core.collection.HLinkedHashSet;
import hype.core.drawable.HDrawable;
import hype.core.util.HMath;

import java.util.ArrayList;

import processing.core.PApplet;

public class HMagneticField extends HBehavior {
	private ArrayList<HMagnet> _magnets;
	private HLinkedHashSet<HDrawable> _targets;
	
	public HMagneticField() {
		_magnets = new ArrayList<HMagneticField.HMagnet>();
		_targets = new HLinkedHashSet<HDrawable>();
	}
	
	public HMagneticField addMagnet(float sx, float sy, float nx, float ny) {
		HMagnet m = new HMagnet();
		m.southx = sx;
		m.southy = sy;
		m.northx = nx;
		m.northy = ny;
		_magnets.add(m);
		return this;
	}
	
	public HMagnet magnet(int index) {
		return _magnets.get(index);
	}
	
	public HMagneticField removeMagnet(int index) {
		_magnets.remove(index);
		return this;
	}
	
	public HMagneticField addTarget(HDrawable d) {
		if(_targets.size() <= 0) register();
		_targets.add(d);
		return this;
	}
	
	public HMagneticField removeTarget(HDrawable d) {
		_targets.remove(d);
		if(_targets.size() <= 0) unregister();
		return this;
	}
	
	public float getRotation(float x, float y) {
		float northRot = 0;
		float southRot = 0;
		
		int numMagnets = _magnets.size();
		for(int i=0; i<numMagnets; i++) {
			HMagnet m = _magnets.get(i);
			northRot += HMath.xAxisAngle(x,y, m.northx,m.northy);
			southRot += HMath.xAxisAngle(x,y, m.southx,m.southy);
		}
		return (northRot + southRot) / numMagnets;
	}
	
	@Override
	public void runBehavior(PApplet app) {
		for(HIterator<HDrawable> it=_targets.iterator(); it.hasNext();) {
			HDrawable d = it.next();
			d.rotationRad( getRotation(d.x(), d.y()) );
		}
	}
	
	@Override
	public HMagneticField register() {
		return (HMagneticField) super.register();
	}
	
	@Override
	public HMagneticField unregister() {
		return (HMagneticField) super.unregister();
	}
	
	
	
	public static class HMagnet {
		public float southx, southy, northx, northy;
	}
}