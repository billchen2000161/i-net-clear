/*
  i-net software provides programming examples for illustration only, without warranty
  either expressed or implied, including, but not limited to, the implied warranties
  of merchantability and/or fitness for a particular purpose. This programming example
  assumes that you are familiar with the programming language being demonstrated and
  the tools used to create and debug procedures. i-net software support professionals
  can help explain the functionality of a particular procedure, but they will not modify
  these examples to provide added functionality or construct procedures to meet your
  specific needs.
  © i-net software 1998-2015
*/

package rdc;

import java.util.Properties;

import com.inet.report.Engine;
import com.inet.report.EngineRenderData;
import com.inet.report.RDC;
import com.inet.report.ReportException;
import com.inet.viewer.RenderData;
import com.inet.viewer.ReportViewer;

/**
 * This class implements the RenderData and PropertiesChecker interfaces in the same way as the class
 * <code>com.inet.report.EngineRenderData</code> does, so this will not be explained. But the constructor and the method
 * <code>checkEngine</code> differs from <code>EngineRenderData</code>. The constructor does not set a report URL, the
 * method <code>checkEngine</code> does not create a engine with a report URL. Both use an interface
 * <code>FillEngineRDC</code>, which enables you to implement arbitrary classes using RDC, by implementing one function
 * containing the RDC calls only. See the samples in package <code>samples.rdc</code>.
 * @see EngineRenderData
 */
public class EngineRDCRenderData extends EngineRenderData {

    private EngineCreator engineCreator;

    /**
     * Use this constructor to set an implementation of the <code>EngineCreator</code> interface to this object.
     * @param engineCreator interface to create and fill engine
     */
    public EngineRDCRenderData( EngineCreator engineCreator ) {
        super( "" );
        if( engineCreator != null ) {
            this.engineCreator = engineCreator;
        } else {
            throw new IllegalArgumentException( "EngineCreator is null" );
        }
    }

    /**
     * Creates or load an <code>Engine</code> and set all needed properties. Do NOT execute the <code>Engine</code>
     * here!
     * @param props Properties to use for creating the engine.
     * @return the created engine
     * @throws ReportException if the specified export format is invalid.
     */
    @Override
    protected Engine createEngine( Properties props ) throws ReportException {
        Engine e = engineCreator.createAndFillEngine( props.getProperty( "export_fmt" ) );
        RDC.setEngineParams( e, props, getPropertiesChecker(), null );
        return e;
    }

    /**
     * "Clones" this RenderData object with all its properties and returns the copy. Useful for deriving from existing
     * RenderData objects by copying them and adding or changing properties. This method is called by the viewer for
     * drilling down, for example - the drilldown property is set on the copy while all other properties remain the
     * same, and the copy is used to open a new report view.
     * @return A cloned copy of this RenderData object with all its properties.
     * @see ReportViewer#addNewReportView(RenderData)
     * @see EngineRenderData#getProperties()
     */
    @Override
    public RenderData getCopy() {
        EngineRDCRenderData rd = new EngineRDCRenderData( engineCreator );
        rd.getProperties().putAll( getProperties() );
        return rd;
    }

}
