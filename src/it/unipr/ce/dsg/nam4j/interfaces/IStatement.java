package it.unipr.ce.dsg.nam4j.interfaces;

public interface IStatement {

	/**
     * get the Subject name.
     *
     * @return the name of the Subject.
     */
	String getName();
	
	/**
     * set the Subject id.
     *
     * @param id.
     */
	void setId(String id);
	
	/**
     * get the Subject id.
     *
     * @return the id of the Subject.
     */
	String getId();
	
	/**
     * get the Subject Content.
     *
     * @return the content of the Subject.
     */
	Object getSubject();
	
	/**
     * set the Subject Content.
     *
     * @param subject.
     */
	void setSubject(Object subject);
	
	/**
     * get the Object Content.
     *
     * @return the content of the Object.
     */
	Object getObject();
	
	/**
     * set the Object Content.
     *
     * @param object.
     */
	void setObject(Object object);
	
	/**
     * get the Location Content.
     *
     * @return the content of the Location.
     */
	Object getLocation();
	
	/**
     * set the Location Content.
     *
     * @param location.
     */
	void setLocation(Object location);
	
	/**
     * get the Action Content.
     *
     * @return the content of the Action.
     */
	Object getAction();
	
	/**
     * set the Action Content.
     *
     * @param action.
     */
	void setAction(Object action);
}
