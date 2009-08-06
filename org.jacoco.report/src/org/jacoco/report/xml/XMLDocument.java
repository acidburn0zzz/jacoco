/*******************************************************************************
 * Copyright (c) 2009 Mountainminds GmbH & Co. KG and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marc R. Hoffmann - initial API and implementation
 *    
 * $Id: $
 *******************************************************************************/
package org.jacoco.report.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Root element of an XML document. Each instance represents a separate output
 * document.
 * 
 * @see XMLElement
 * @author Marc R. Hoffmann
 * @version $Revision: $
 */
public class XMLDocument extends XMLElement {

	/** All documents created by this API are created in UTF-8. */
	public static final String ENCODING = "UTF-8";

	/** XML header string */
	public static final String XMLHEADER = "<?xml version=\"1.0\" encoding=\""
			+ ENCODING + "\"?>";

	/**
	 * Writes a new document to the given writer. The document might contain a
	 * document type declaration.
	 * 
	 * @param rootnode
	 *            name of the root node
	 * @param pubId
	 *            optional doctype identifier or <code>null</code>
	 * @param system
	 *            system reference, required if doctype is given
	 * @param writer
	 *            writer for content output
	 * @throws IOException
	 *             in case of problems with the writer
	 */
	public XMLDocument(final String rootnode, final String pubId,
			final String system, final Writer writer) throws IOException {
		super(writer, rootnode);
		writeHeader(rootnode, pubId, system, writer);
		beginOpenTag();
	}

	/**
	 * Writes a new document to the given binary stream, which will be encoded
	 * in {@link #ENCODING}. The document might contain a document type
	 * declaration.
	 * 
	 * @param rootnode
	 *            name of the root node
	 * @param pubId
	 *            optional doctype identifier or <code>null</code>
	 * @param system
	 *            system reference, required if doctype is given
	 * @param output
	 *            output for content output
	 * @throws IOException
	 *             in case of problems with the writer
	 */
	public XMLDocument(final String rootnode, final String pubId,
			final String system, final OutputStream output) throws IOException {
		this(rootnode, pubId, system, new OutputStreamWriter(output, ENCODING));
	}

	@Override
	public void close() throws IOException {
		super.close();
		writer.close();
	}

	private static void writeHeader(final String rootnode, final String pubId,
			final String system, final Writer writer) throws IOException {
		writer.write(XMLHEADER);
		if (pubId != null) {
			writer.write("<!DOCTYPE ");
			writer.write(rootnode);
			writer.write(" PUBLIC \"");
			writer.write(pubId);
			writer.write("\" \"");
			writer.write(system);
			writer.write("\">");
		}
	}

}