<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
	<xsl:output method="xml" />

	<!-- Adds Element Before Types -->
	<xsl:template match="wsdl:types">
		<wsp:Policy wsu:Id="#UsernameToken"
			xmlns:wsp="http://schemas.xmlsoap.org/ws/2004/09/policy"
			xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
			<wsp:ExactlyOne>
				<wsp:All>
					<sp:SecurityToken xmlns:sp="http://schemas.xmlsoap.org/ws/2002/12/secext">
						<sp:TokenType>sp:UsernameToken</sp:TokenType>
					</sp:SecurityToken>
				</wsp:All>
			</wsp:ExactlyOne>
		</wsp:Policy>
	</xsl:template>

</xsl:stylesheet>