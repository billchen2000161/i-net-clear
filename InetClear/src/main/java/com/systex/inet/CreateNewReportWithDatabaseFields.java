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

package com.systex.inet;

import java.io.FileOutputStream;

import com.inet.report.Area;
import com.inet.report.DatabaseField;
import com.inet.report.DatabaseTables;
import com.inet.report.Datasource;
import com.inet.report.Engine;
import com.inet.report.Field;
import com.inet.report.Fields;
import com.inet.report.RDC;
import com.inet.report.Section;
import com.inet.report.TableSource;

import rdc.RDCSample;

/**
 * This sample shows how to create a new report and add some database fields to it. The Data Source Configuration
 * "Sample Database" is necessary for this sample. You can create it using the Data Source Configuration Manager in
 * i-net Designer or Configuration Manager.
 */
public class CreateNewReportWithDatabaseFields extends RDCSample {

    static final String[] CUSTOMERS_NAMES         = { "CustomerID", "CustomerName", "ContactName", "ContactTitle", "ContactPosition",
        "LastYearsSales", "Address", "Address2", "City", "Region", "Country", "PostalCode", "Phone", "Fax" };
    static final int[]    CUSTOMERS_TYPES         = { Field.STRING, Field.STRING, Field.STRING, Field.STRING, Field.STRING, Field.NUMBER,
        Field.STRING, Field.STRING, Field.STRING, Field.STRING, Field.STRING, Field.STRING, Field.STRING, Field.STRING };
    static final String[] EMPLOYEE_ADRESSES_NAMES = { "EmployeeID", "Address1", "Address2", "City", "Region", "Country", "PostalCode" };
    static final int[]    EMPLOYEE_ADRESSES_TYPES = { Field.NUMBER, Field.STRING, Field.STRING, Field.STRING, Field.STRING, Field.STRING,
        Field.STRING                             };

    public Engine createAndFillEngine( String exportFmt ) {
        try {
            // Create an Engine for a new report
            Engine eng = RDC.createEmptyEngine( exportFmt );

            // Fill the engine
            DatabaseTables dbTables = eng.getDatabaseTables();
            Fields fields = eng.getFields();

            // Define a data source
            Datasource ds = dbTables.createDatasource( "Sample Database" ); // Data Source Configuration "Sample Database"

            // Define the tables of the data source
            TableSource ts_Customers = ds.createTableSource( "Customers" );
            for( int colIdx = 0; colIdx < CUSTOMERS_NAMES.length; colIdx++ ) {
                ts_Customers.addColumn( CUSTOMERS_NAMES[colIdx], CUSTOMERS_TYPES[colIdx] );
            }

            TableSource ts_EmployeeAdresses = ds.createTableSource( "EmployeeAddresses" );
            for( int colIdx = 0; colIdx < EMPLOYEE_ADRESSES_NAMES.length; colIdx++ ) {
                ts_EmployeeAdresses.addColumn( EMPLOYEE_ADRESSES_NAMES[colIdx], EMPLOYEE_ADRESSES_TYPES[colIdx] );
            }

            // Join both tables
            dbTables.addJoin( ts_Customers.getAlias(), "City", ts_EmployeeAdresses.getAlias(), "City", DatabaseTables.JOINTYPE_INNER,
                              DatabaseTables.JOINLINK_EQUALS );

            DatabaseField dbField = null;
            Area dArea = eng.getArea( "D" );
            Section dSec = dArea.getSection( 0 );

            dbField = fields.getDatabaseField( ts_Customers.getAlias() + "." + "CustomerID" );
            dSec.addFieldElement( dbField, 100, 100, 2000, 500 );

            dbField = fields.getDatabaseField( ts_EmployeeAdresses.getAlias() + "." + "City" );
            dSec.addFieldElement( dbField, 3000, 100, 2000, 500 );

            return eng;
        } catch( Throwable e ) {
            e.printStackTrace();
            System.exit( 0 );
            return null;
        }
    }
    
}
