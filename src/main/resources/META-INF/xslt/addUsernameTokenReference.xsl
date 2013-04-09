<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
	<xsl:output method="xml" />

	<!-- Adds Element Before Types -->
	<xsl:template match="wsdl:operation">
		<wsp:PolicyReference xmlns:wsp="http://schemas.xmlsoap.org/ws/2004/09/policy"
			URI="#UsernameToken" />
	</xsl:template>

</xsl:stylesheet>