#include <precompiled.h>
#include <nova/standard/nova_standard_Nova_String.h>

typedef char (*l0_1_Nova_transform)(void*, nova_standard_exception_Nova_ExceptionData*, char);
typedef char (*l0_2_Nova_transform)(void*, nova_standard_exception_Nova_ExceptionData*, char);
typedef char (*l0_3_Nova_transform)(void*, nova_standard_exception_Nova_ExceptionData*, char);

nova_VTable_nova_standard_Nova_String nova_VTable_nova_standard_Nova_String_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_String_0_Nova_toString,
	nova_standard_Nova_String_Nova_equals,
	nova_standard_Nova_String_0_Nova_concat,
};

int nova_standard_Nova_String_Nova_calculateLength(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
int nova_standard_Nova_String_0_Nova_indexOf(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_search, int l0_Nova_start, int l0_Nova_direction);
char nova_standard_Nova_String_Nova_containsChar(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_needle, char* l0_Nova_Chars, int l0_Nova_length);
nova_standard_Nova_String* nova_standard_Nova_String_Nova_changeCase(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, l0_3_Nova_transform l0_Nova_transform, void* l0_ref_Nova_transform);
int nova_standard_Nova_String_Nova_whitespaceLength;
char* nova_standard_Nova_String_Nova_whitespace;
void nova_standard_Nova_StringNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
		nova_standard_Nova_String_Nova_whitespaceLength = 4;
		nova_standard_Nova_String_Nova_whitespace = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char*[4]));
		nova_standard_Nova_String_Nova_whitespace[0] = ' ';
		nova_standard_Nova_String_Nova_whitespace[1] = '\t';
		nova_standard_Nova_String_Nova_whitespace[2] = '\n';
		nova_standard_Nova_String_Nova_whitespace[3] = '\r';
	}
}

nova_standard_Nova_String* nova_standard_Nova_String_0_Nova_construct(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_c)
{
	CCLASS_NEW(nova_standard_Nova_String, this,);
	this->vtable = &nova_VTable_nova_standard_Nova_String_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_Nova_String_Nova_super(this, exceptionData);
	
	{
		nova_standard_Nova_String_0_Nova_this(this, exceptionData, l0_Nova_c);
	}
	
	return this;
}

nova_standard_Nova_String* nova_standard_Nova_String_1_Nova_construct(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char* l0_Nova_chars)
{
	CCLASS_NEW(nova_standard_Nova_String, this,);
	this->vtable = &nova_VTable_nova_standard_Nova_String_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_Nova_String_Nova_super(this, exceptionData);
	
	{
		nova_standard_Nova_String_1_Nova_this(this, exceptionData, l0_Nova_chars);
	}
	
	return this;
}

void nova_standard_Nova_String_Nova_destroy(nova_standard_Nova_String** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_Nova_String_0_Nova_this(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_c)
{
	char* l2_Nova_chars;
	
	l2_Nova_chars = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char*[2]));
	l2_Nova_chars[0] = l0_Nova_c;
	l2_Nova_chars[1] = '\0';
	nova_standard_Nova_String_1_Nova_this(this, exceptionData, l2_Nova_chars);
}

void nova_standard_Nova_String_1_Nova_this(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char* l0_Nova_chars)
{
	this->nova_standard_Nova_String_Nova_chars = l0_Nova_chars;
	this->nova_standard_Nova_String_Nova_length = nova_standard_Nova_String_Nova_calculateLength(this, exceptionData);
	if (l0_Nova_chars[this->nova_standard_Nova_String_Nova_length] != '\0')
	{
		l0_Nova_chars[this->nova_standard_Nova_String_Nova_length] = '\0';
	}
}

int nova_standard_Nova_String_Nova_calculateLength(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (int)strlen((char*)(this->nova_standard_Nova_String_Nova_chars));
}

nova_standard_Nova_String* nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_str)
{
	int l1_Nova_size;
	char* l1_Nova_newData;
	nova_standard_Nova_String* l1_Nova_newStr;
	
	l0_Nova_str = l0_Nova_str->vtable->nova_standard_Nova_String_virtual0_Nova_toString(l0_Nova_str, exceptionData);
	l1_Nova_size = l0_Nova_str->nova_standard_Nova_String_Nova_length + this->nova_standard_Nova_String_Nova_length + 1;
	l1_Nova_newData = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char*[l1_Nova_size]));
	strcpy((char*)(l1_Nova_newData), (char*)(this->nova_standard_Nova_String_Nova_chars));
	strcat((char*)(l1_Nova_newData), (char*)(l0_Nova_str->nova_standard_Nova_String_Nova_chars));
	l1_Nova_newData[l1_Nova_size - 1] = '\0';
	l1_Nova_newStr = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, l1_Nova_newData);
	return l1_Nova_newStr;
}

char nova_standard_Nova_String_Nova_equals(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_other)
{
	int l3_Nova_i;
	
	if (this->nova_standard_Nova_String_Nova_length != l0_Nova_other->nova_standard_Nova_String_Nova_length)
	{
		return 0;
	}
	l3_Nova_i = 0;
	for (; l3_Nova_i < this->nova_standard_Nova_String_Nova_length; l3_Nova_i++)
	{
		if (this->nova_standard_Nova_String_Nova_chars[l3_Nova_i] != l0_Nova_other->nova_standard_Nova_String_Nova_chars[l3_Nova_i])
		{
			return 0;
		}
	}
	return 1;
}

int nova_standard_Nova_String_0_Nova_indexOf(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_search, int l0_Nova_start, int l0_Nova_direction)
{
	int l2_Nova_i;
	
	l2_Nova_i = l0_Nova_start;
	for (; l2_Nova_i < this->nova_standard_Nova_String_Nova_length && l2_Nova_i >= 0; l2_Nova_i = l2_Nova_i + l0_Nova_direction)
	{
		char l2_Nova_found;
		int l3_Nova_j;
		
		l2_Nova_found = 1;
		l3_Nova_j = 0;
		for (; l3_Nova_j < l0_Nova_search->nova_standard_Nova_String_Nova_length && l2_Nova_i + l3_Nova_j < this->nova_standard_Nova_String_Nova_length && l2_Nova_found; l3_Nova_j++)
		{
			if (l0_Nova_search->nova_standard_Nova_String_Nova_chars[l3_Nova_j] != this->nova_standard_Nova_String_Nova_chars[l2_Nova_i + l3_Nova_j])
			{
				l2_Nova_found = 0;
			}
		}
		if (l2_Nova_found)
		{
			return l2_Nova_i;
		}
	}
	return -1;
}

int nova_standard_Nova_String_1_Nova_indexOf(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_search)
{
	return nova_standard_Nova_String_0_Nova_indexOf(this, exceptionData, l0_Nova_search, 0, 1);
}

int nova_standard_Nova_String_2_Nova_indexOf(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_search, int l0_Nova_start)
{
	return nova_standard_Nova_String_0_Nova_indexOf(this, exceptionData, l0_Nova_search, l0_Nova_start, 1);
}

int nova_standard_Nova_String_Nova_lastIndexOf(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_search)
{
	return nova_standard_Nova_String_0_Nova_indexOf(this, exceptionData, l0_Nova_search, this->nova_standard_Nova_String_Nova_length - 1, -1);
}

nova_standard_Nova_String* nova_standard_Nova_String_0_Nova_substring(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_start, int l0_Nova_end)
{
	char* l1_Nova_buf;
	
	if (l0_Nova_end - l0_Nova_start == 0)
	{
		return nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "");
	}
	else if (l0_Nova_end - l0_Nova_start < 0)
	{
		nova_standard_Nova_String* nova_local_0;
		nova_standard_Nova_String* nova_local_1;
		
		nova_local_0 = nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, l0_Nova_start);
		nova_local_1 = nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, l0_Nova_end);
		THROW(1, nova_standard_exception_Nova_Exception_1_Nova_construct(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Substring bounds of ["), exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", "), exceptionData, nova_local_1->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_1, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "] are invalid")))))));
	}
	l1_Nova_buf = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char*[l0_Nova_end - l0_Nova_start + 1]));
	memcpy((char*)(l1_Nova_buf), (char**)(nova_standard_primitive_number_Nova_Char_5_Nova_construct(0, exceptionData, this->nova_standard_Nova_String_Nova_chars[l0_Nova_start])), (int)(l0_Nova_end - l0_Nova_start));
	l1_Nova_buf[l0_Nova_end - l0_Nova_start] = '\0';
	return nova_standard_Nova_String_1_Nova_construct(0, exceptionData, l1_Nova_buf);
}

nova_standard_Nova_String* nova_standard_Nova_String_1_Nova_substring(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_start)
{
	return nova_standard_Nova_String_0_Nova_substring(this, exceptionData, l0_Nova_start, this->nova_standard_Nova_String_Nova_length);
}

char nova_standard_Nova_String_Nova_lastChar(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_Nova_String_Nova_charAt(this, exceptionData, this->nova_standard_Nova_String_Nova_length - 1);
}

char nova_standard_Nova_String_Nova_charAt(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_index)
{
	return this->nova_standard_Nova_String_Nova_chars[l0_Nova_index];
}

nova_standard_Nova_String* nova_standard_Nova_String_Nova_trim(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	int l1_Nova_start;
	int l1_Nova_end;
	
	l1_Nova_start = 0;
	l1_Nova_end = this->nova_standard_Nova_String_Nova_length - 1;
	while (l1_Nova_start < this->nova_standard_Nova_String_Nova_length && nova_standard_Nova_String_Nova_containsChar(this, exceptionData, this->nova_standard_Nova_String_Nova_chars[l1_Nova_start], nova_standard_Nova_String_Nova_whitespace, nova_standard_Nova_String_Nova_whitespaceLength))
	{
		l1_Nova_start++;
	}
	while (l1_Nova_end >= 0 && nova_standard_Nova_String_Nova_containsChar(this, exceptionData, this->nova_standard_Nova_String_Nova_chars[l1_Nova_end], nova_standard_Nova_String_Nova_whitespace, nova_standard_Nova_String_Nova_whitespaceLength))
	{
		l1_Nova_end--;
	}
	if (l1_Nova_end == 0)
	{
		return nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "");
	}
	if (l1_Nova_start == 0 && l1_Nova_end == this->nova_standard_Nova_String_Nova_length - 1)
	{
		return this;
	}
	return nova_standard_Nova_String_0_Nova_substring(this, exceptionData, l1_Nova_start, l1_Nova_end + 1);
}

char nova_standard_Nova_String_Nova_containsChar(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_needle, char* l0_Nova_Chars, int l0_Nova_length)
{
	int l2_Nova_i;
	
	l2_Nova_i = 0;
	for (; l2_Nova_i < l0_Nova_length; l2_Nova_i++)
	{
		if (l0_Nova_needle == l0_Nova_Chars[l2_Nova_i])
		{
			return 1;
		}
	}
	return 0;
}

nova_standard_Nova_String* nova_standard_Nova_String_Nova_toLowerCase(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_Nova_String_Nova_changeCase(this, exceptionData, (l0_3_Nova_transform)&nova_standard_primitive_number_Nova_Char_1_Nova_toLowerCase, 0);
}

nova_standard_Nova_String* nova_standard_Nova_String_Nova_toUpperCase(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_Nova_String_Nova_changeCase(this, exceptionData, (l0_3_Nova_transform)&nova_standard_primitive_number_Nova_Char_Nova_toUpperCase, 0);
}

nova_standard_Nova_String* nova_standard_Nova_String_Nova_changeCase(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, l0_3_Nova_transform l0_Nova_transform, void* l0_ref_Nova_transform)
{
	char* l1_Nova_newData;
	int l2_Nova_i;
	
	l1_Nova_newData = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char*[this->nova_standard_Nova_String_Nova_length]));
	l2_Nova_i = 0;
	for (; l2_Nova_i < this->nova_standard_Nova_String_Nova_length; l2_Nova_i++)
	{
		l1_Nova_newData[l2_Nova_i] = l0_Nova_transform(l0_ref_Nova_transform, exceptionData, this->nova_standard_Nova_String_Nova_chars[l2_Nova_i]);
	}
	return nova_standard_Nova_String_1_Nova_construct(0, exceptionData, l1_Nova_newData);
}

nova_standard_Nova_String* nova_standard_Nova_String_0_Nova_getDataBetween(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_before, nova_standard_Nova_String* l0_Nova_after)
{
	return nova_standard_Nova_String_1_Nova_getDataBetween(this, exceptionData, l0_Nova_before, l0_Nova_after, 0);
}

nova_standard_Nova_String* nova_standard_Nova_String_1_Nova_getDataBetween(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_before, nova_standard_Nova_String* l0_Nova_after, int l0_Nova_start)
{
	int l1_Nova_s;
	int l1_Nova_e;
	
	l1_Nova_s = nova_standard_Nova_String_2_Nova_indexOf(this, exceptionData, l0_Nova_before, l0_Nova_start);
	l1_Nova_e = nova_standard_Nova_String_2_Nova_indexOf(this, exceptionData, l0_Nova_after, l1_Nova_s + 1);
	if (l1_Nova_s >= 0 && l1_Nova_e > 0)
	{
		return nova_standard_Nova_String_0_Nova_substring(this, exceptionData, l1_Nova_s + l0_Nova_before->nova_standard_Nova_String_Nova_length, l1_Nova_e);
	}
	return nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "");
}

nova_standard_Nova_String* nova_standard_Nova_String_0_Nova_toString(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this;
}

void nova_standard_Nova_String_Nova_super(nova_standard_Nova_String* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_Nova_String_Nova_length = 0;
	this->nova_standard_Nova_String_Nova_chars = 0;
}

