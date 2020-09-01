package me.deftware.client.framework.render.shader;

/**
 * @author Deftware
 */
public interface IShaderProvider {
	
	void compileVertexShader(int vs) throws Exception;

	void compileFragmentShader(int fs) throws Exception;

	void setupUniforms();
	
	String getName();
	
}
