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

import com.inet.report.Engine;

/**
 * Interface used to create new reports and fill it with elements.
 */
public interface EngineCreator {

    /**
     * Method to create and fill the engine for a report.
     * @param exportFmt the output format (e.g. Engine.EXPORT_PDF)
     * @return the new engine for the report
     */
    public Engine createAndFillEngine( String exportFmt );

}
