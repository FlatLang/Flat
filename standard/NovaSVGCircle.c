#include <precompiled.h>
#include "NovaSVGCircle.h"


nova_VTable_SVGCircle nova_VTable_SVGCircle_val =
{
	nova_1_SVGCircle_generateOutput,
	nova_1_SVGCircle_toString,
};

SVGCircle* nova_SVGCircle_SVGCircle(SVGCircle* this, ExceptionData* exceptionData, double nova_0_x, double nova_0_y, int nova_0_r)
{
	CCLASS_NEW(SVGCircle, this,);
	
	this->nova_SVGCircle_x = 0;
	this->nova_SVGCircle_y = 0;
	this->nova_SVGCircle_r = 0;
	this->vtable = &nova_VTable_SVGCircle_val;
	{
		this->nova_SVGCircle_x = nova_0_x;
		this->nova_SVGCircle_y = nova_0_y;
		this->nova_SVGCircle_r = nova_0_r;
	}
	
	return this;
}

void nova_del_SVGCircle(SVGCircle** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_1_SVGCircle_generateOutput(SVGCircle* this, ExceptionData* exceptionData, File* nova_0_file)
{
	nova_File_write(nova_0_file, exceptionData, nova_String_concat(nova_String_String(0, exceptionData, (char*)("<circle cx=\"")), exceptionData, nova_String_concat(nova_2_Double_toString(nova_Double_Double(0, exceptionData, this->nova_SVGCircle_x), exceptionData), exceptionData, nova_String_concat(nova_String_String(0, exceptionData, (char*)("\" cy=\"")), exceptionData, nova_String_concat(nova_2_Double_toString(nova_Double_Double(0, exceptionData, this->nova_SVGCircle_y), exceptionData), exceptionData, nova_String_concat(nova_String_String(0, exceptionData, (char*)("\" r=\"")), exceptionData, nova_String_concat(nova_2_Integer_toString(nova_Integer_Integer(0, exceptionData, this->nova_SVGCircle_r), exceptionData), exceptionData, nova_String_String(0, exceptionData, (char*)("\" stroke=\"false\" fill=\"black\"/>\n")))))))));
}

String* nova_1_SVGCircle_toString(SVGCircle* this, ExceptionData* exceptionData)
{
	return nova_String_concat(nova_String_String(0, exceptionData, (char*)("[Circle at (")), exceptionData, nova_String_concat(nova_2_Double_toString(nova_Double_Double(0, exceptionData, this->nova_SVGCircle_x), exceptionData), exceptionData, nova_String_concat(nova_String_String(0, exceptionData, (char*)(", ")), exceptionData, nova_String_concat(nova_2_Double_toString(nova_Double_Double(0, exceptionData, this->nova_SVGCircle_y), exceptionData), exceptionData, nova_String_concat(nova_String_String(0, exceptionData, (char*)(") with a radius of ")), exceptionData, nova_String_concat(nova_2_Integer_toString(nova_Integer_Integer(0, exceptionData, this->nova_SVGCircle_r), exceptionData), exceptionData, nova_String_String(0, exceptionData, (char*)("]"))))))));
}
