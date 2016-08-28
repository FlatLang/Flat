#include <precompiled.h>
#include <nova/nova_Nova_String.h>

typedef struct
{
	/* Long hash */ long_long* nova_Nova_String_Nova_hash;
} Context1;


nova_Extension_VTable_String nova_Extension_VTable_String_val =
{
	{
		(int(*)(nova_datastruct_Nova_Comparable*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_String_Nova_compareTo,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_String_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_String_0_Nova_toString,
	nova_Nova_String_Nova_equals,
	nova_Nova_String_Accessor_Nova_hashCodeLong,
	nova_Nova_String_0_Nova_concat,
	nova_Nova_String_Nova_compareTo,
};



int nova_Nova_String_Nova_calculateSize(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, char* nova_Nova_String_Nova_chars);
int nova_Nova_String_0_Nova_indexOf(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_search, int nova_Nova_String_Nova_start, int nova_Nova_String_Nova_direction);
nova_datastruct_list_Nova_CharArray* generated1(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_Nova_String_Nova_testLambda32(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_Nova_String_Nova_c, int nova_Nova_String_Nova_i, nova_datastruct_list_Nova_CharArray* nova_Nova_String_Nova__3, Context1* context);

nova_datastruct_list_Nova_CharArray* nova_Nova_String_Nova_whitespace;
void nova_Nova_String_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
		nova_Nova_String_Nova_whitespace = generated1(0, exceptionData);
	}
}

nova_Nova_String* nova_Nova_String_0_Nova_String(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_Nova_String_Nova_c)
{
	CCLASS_NEW(nova_Nova_String, this,);
	this->vtable = &nova_Extension_VTable_String_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_Nova_String_Nova_super(this, exceptionData);
	
	{
		
		nova_Nova_String_1_Nova_this(this, exceptionData, nova_Nova_String_Nova_c);
	}
	
	return this;
}

nova_Nova_String* nova_Nova_String_1_Nova_String(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, char* nova_Nova_String_Nova_chars)
{
	CCLASS_NEW(nova_Nova_String, this,);
	this->vtable = &nova_Extension_VTable_String_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_Nova_String_Nova_super(this, exceptionData);
	
	{
		nova_Nova_String_2_Nova_this(this, exceptionData, nova_Nova_String_Nova_chars);
	}
	
	return this;
}

void nova_Nova_String_Nova_destroy(nova_Nova_String** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_datastruct_list_Nova_CharArray_Nova_destroy(&(*this)->nova_Nova_String_Nova_chars, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_Nova_String_1_Nova_this(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_Nova_String_Nova_c)
{
	char* l2_Nova_chars = (char*)nova_null;
	
	
	l2_Nova_chars = (char*)NOVA_MALLOC(sizeof(nova_primitive_number_Nova_Char) * 2);
	l2_Nova_chars[0] = nova_Nova_String_Nova_c;
	l2_Nova_chars[1] = '\0';
	nova_Nova_String_2_Nova_this(this, exceptionData, l2_Nova_chars);
}

void nova_Nova_String_2_Nova_this(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, char* nova_Nova_String_Nova_chars)
{
	this->nova_Nova_String_Nova_count = nova_Nova_String_Nova_calculateSize(this, exceptionData, nova_Nova_String_Nova_chars);
	this->nova_Nova_String_Nova_chars = nova_datastruct_list_Nova_CharArray_2_Nova_CharArray(0, exceptionData, nova_Nova_String_Nova_chars, this->nova_Nova_String_Nova_count);
	if (nova_Nova_String_Nova_chars[this->nova_Nova_String_Nova_count] != '\0')
	{
		nova_Nova_String_Nova_chars[this->nova_Nova_String_Nova_count] = '\0';
	}
}

int nova_Nova_String_Nova_calculateSize(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, char* nova_Nova_String_Nova_chars)
{
	return (int)strlen(nova_Nova_String_Nova_chars);
}

nova_Nova_String* nova_Nova_String_0_Nova_concat(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_str)
{
	int l1_Nova_sz = 0;
	char* l1_Nova_newData = (char*)nova_null;
	
	l1_Nova_sz = nova_Nova_String_Nova_str->nova_Nova_String_Nova_count + this->nova_Nova_String_Nova_count + 1;
	
	l1_Nova_newData = (char*)NOVA_MALLOC(sizeof(nova_primitive_number_Nova_Char) * l1_Nova_sz);
	strcpy(l1_Nova_newData, (char*)(this->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data));
	strcat(l1_Nova_newData, (char*)(nova_Nova_String_Nova_str->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data));
	l1_Nova_newData[l1_Nova_sz - 1] = '\0';
	return nova_Nova_String_1_Nova_String(0, exceptionData, l1_Nova_newData);
}

char nova_Nova_String_Nova_equals(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_other)
{
	return nova_datastruct_Nova_Comparable_virtual0_Nova_compareTo((nova_datastruct_Nova_Comparable*)(this), exceptionData, (nova_Nova_Object*)(nova_Nova_String_Nova_other)) == 0;
}

char nova_Nova_String_Nova_contains(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_search)
{
	return (char)nova_Nova_String_1_Nova_indexOf(this, exceptionData, nova_Nova_String_Nova_search) >= 0;
}

int nova_Nova_String_0_Nova_indexOf(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_search, int nova_Nova_String_Nova_start, int nova_Nova_String_Nova_direction)
{
	int l1_Nova_i = 0;
	
	l1_Nova_i = nova_Nova_String_Nova_start;
	while (l1_Nova_i < this->nova_Nova_String_Nova_count && l1_Nova_i >= 0)
	{
		char l1_Nova_found = 0;
		int l1_Nova_j = 0;
		
		l1_Nova_found = 1;
		l1_Nova_j = (int)(0);
		while (l1_Nova_j < nova_Nova_String_Nova_search->nova_Nova_String_Nova_count && l1_Nova_i + l1_Nova_j < this->nova_Nova_String_Nova_count && l1_Nova_found)
		{
			if ((char)(intptr_t)nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(nova_Nova_String_Nova_search->nova_Nova_String_Nova_chars), exceptionData, l1_Nova_j) != (char)(intptr_t)nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(this->nova_Nova_String_Nova_chars), exceptionData, l1_Nova_i + l1_Nova_j))
			{
				l1_Nova_found = 0;
			}
			l1_Nova_j++;
		}
		if (l1_Nova_found)
		{
			return l1_Nova_i;
		}
		l1_Nova_i = l1_Nova_i + nova_Nova_String_Nova_direction;
	}
	return (int)-1;
}

int nova_Nova_String_1_Nova_indexOf(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_search)
{
	return nova_Nova_String_0_Nova_indexOf(this, exceptionData, nova_Nova_String_Nova_search, 0, 1);
}

int nova_Nova_String_2_Nova_indexOf(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_search, int nova_Nova_String_Nova_start)
{
	return nova_Nova_String_0_Nova_indexOf(this, exceptionData, nova_Nova_String_Nova_search, nova_Nova_String_Nova_start, 1);
}

int nova_Nova_String_Nova_lastIndexOf(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_search)
{
	return nova_Nova_String_0_Nova_indexOf(this, exceptionData, nova_Nova_String_Nova_search, this->nova_Nova_String_Nova_count - 1, -1);
}

nova_Nova_String* nova_Nova_String_0_Nova_substring(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_Nova_String_Nova_start, int nova_Nova_String_Nova_end)
{
	char* l1_Nova_buf = (char*)nova_null;
	char* l1_Nova_arr = (char*)nova_null;
	
	if (nova_Nova_String_Nova_end - nova_Nova_String_Nova_start <= 0)
	{
		if (nova_Nova_String_Nova_end - nova_Nova_String_Nova_start < 0)
		{
			THROW(1, nova_exception_Nova_Exception_1_Nova_Exception(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, "Substring bounds of ["), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, nova_Nova_String_Nova_start)), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, ", "), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, nova_Nova_String_Nova_end)), exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "] are invalid")))))));
		}
		return nova_Nova_String_1_Nova_String(0, exceptionData, "");
	}
	
	l1_Nova_buf = (char*)NOVA_MALLOC(sizeof(nova_primitive_number_Nova_Char) * nova_Nova_String_Nova_end - nova_Nova_String_Nova_start + 1);
	
	l1_Nova_arr = (char*)(this->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data);
	memcpy(l1_Nova_buf, &l1_Nova_arr[nova_Nova_String_Nova_start], nova_Nova_String_Nova_end - nova_Nova_String_Nova_start);
	l1_Nova_buf[nova_Nova_String_Nova_end - nova_Nova_String_Nova_start] = '\0';
	return nova_Nova_String_1_Nova_String(0, exceptionData, l1_Nova_buf);
}

nova_Nova_String* nova_Nova_String_1_Nova_substring(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_Nova_String_Nova_start)
{
	return nova_Nova_String_0_Nova_substring(this, exceptionData, nova_Nova_String_Nova_start, this->nova_Nova_String_Nova_count);
}

char nova_Nova_String_Nova_lastChar(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_Nova_charAt(this, exceptionData, this->nova_Nova_String_Nova_count - 1);
}

char nova_Nova_String_Nova_charAt(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_Nova_String_Nova_index)
{
	return (char)(intptr_t)nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(this->nova_Nova_String_Nova_chars), exceptionData, nova_Nova_String_Nova_index);
}

nova_Nova_String* nova_Nova_String_Nova_trim(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	int l1_Nova_start = 0;
	int l1_Nova_end = 0;
	
	l1_Nova_start = (int)(0);
	l1_Nova_end = this->nova_Nova_String_Nova_count - 1;
	while (l1_Nova_start < this->nova_Nova_String_Nova_count && nova_datastruct_list_Nova_List_virtual0_Nova_contains((nova_datastruct_list_Nova_List*)(nova_Nova_String_Nova_whitespace), exceptionData, (nova_Nova_Object*)(intptr_t)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(this->nova_Nova_String_Nova_chars), exceptionData, l1_Nova_start))))
	{
		l1_Nova_start++;
	}
	while (l1_Nova_end >= 0 && nova_datastruct_list_Nova_List_virtual0_Nova_contains((nova_datastruct_list_Nova_List*)(nova_Nova_String_Nova_whitespace), exceptionData, (nova_Nova_Object*)(intptr_t)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(this->nova_Nova_String_Nova_chars), exceptionData, l1_Nova_end))))
	{
		l1_Nova_end--;
	}
	if (l1_Nova_end == 0)
	{
		return nova_Nova_String_1_Nova_String(0, exceptionData, "");
	}
	if (l1_Nova_start == 0 && l1_Nova_end == this->nova_Nova_String_Nova_count - 1)
	{
		return this;
	}
	return nova_Nova_String_0_Nova_substring(this, exceptionData, l1_Nova_start, l1_Nova_end + 1);
}

nova_Nova_String* nova_Nova_String_Nova_toLowerCase(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_Nova_transform(this, exceptionData, (nova_Nova_String_closure3_Nova_transform)&nova_primitive_number_Nova_Char_1_Nova_toLowerCase, 0, nova_null);
}

nova_Nova_String* nova_Nova_String_Nova_toUpperCase(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_Nova_transform(this, exceptionData, (nova_Nova_String_closure3_Nova_transform)&nova_primitive_number_Nova_Char_1_Nova_toUpperCase, 0, nova_null);
}

nova_Nova_String* nova_Nova_String_Nova_capitalize(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (this->nova_Nova_String_Nova_count == 0)
	{
		return this;
	}
	return nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Char_2_Nova_toString(0, exceptionData, nova_primitive_number_Nova_Char_1_Nova_toUpperCase(0, exceptionData, (char)(intptr_t)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(this->nova_Nova_String_Nova_chars), exceptionData, 0))))), exceptionData, nova_Nova_String_1_Nova_substring(this, exceptionData, 1));
}

nova_Nova_String* nova_Nova_String_Nova_transform(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String_closure3_Nova_transform nova_Nova_String_Nova_transform, void* nova_Nova_String_ref_Nova_transform, void* transform_context)
{
	char* l1_Nova_newData = (char*)nova_null;
	int l2_Nova_i = 0;
	
	
	l1_Nova_newData = (char*)NOVA_MALLOC(sizeof(nova_primitive_number_Nova_Char) * this->nova_Nova_String_Nova_count);
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)this->nova_Nova_String_Nova_count; l2_Nova_i++)
	{
		l1_Nova_newData[l2_Nova_i] = nova_Nova_String_Nova_transform(nova_Nova_String_ref_Nova_transform, exceptionData, (char)(intptr_t)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(this->nova_Nova_String_Nova_chars), exceptionData, l2_Nova_i)), l2_Nova_i, transform_context);
	}
	return nova_Nova_String_1_Nova_String(0, exceptionData, l1_Nova_newData);
}

nova_Nova_String* nova_Nova_String_0_Nova_getStringBetween(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_before, nova_Nova_String* nova_Nova_String_Nova_after)
{
	return nova_Nova_String_1_Nova_getStringBetween(this, exceptionData, nova_Nova_String_Nova_before, nova_Nova_String_Nova_after, 0);
}

nova_Nova_String* nova_Nova_String_1_Nova_getStringBetween(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_before, nova_Nova_String* nova_Nova_String_Nova_after, int nova_Nova_String_Nova_start)
{
	int l1_Nova_s = 0;
	int l1_Nova_e = 0;
	
	l1_Nova_s = nova_Nova_String_2_Nova_indexOf(this, exceptionData, nova_Nova_String_Nova_before, nova_Nova_String_Nova_start);
	l1_Nova_e = nova_Nova_String_2_Nova_indexOf(this, exceptionData, nova_Nova_String_Nova_after, l1_Nova_s + 1);
	if (l1_Nova_s >= 0 && l1_Nova_e > 0)
	{
		return nova_Nova_String_0_Nova_substring(this, exceptionData, l1_Nova_s + nova_Nova_String_Nova_before->nova_Nova_String_Nova_count, l1_Nova_e);
	}
	return nova_Nova_String_1_Nova_String(0, exceptionData, "");
}

int nova_Nova_String_Nova_compareTo(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_other)
{
	long_long l1_Nova_min = 0;
	int l2_Nova_i = 0;
	
	l1_Nova_min = nova_math_Nova_Math_Nova_min(0, exceptionData, this->nova_Nova_String_Nova_count, (long_long)(nova_Nova_String_Nova_other->nova_Nova_String_Nova_count));
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)l1_Nova_min; l2_Nova_i++)
	{
		if ((char)(intptr_t)nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(this->nova_Nova_String_Nova_chars), exceptionData, l2_Nova_i) - (char)(intptr_t)nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(nova_Nova_String_Nova_other->nova_Nova_String_Nova_chars), exceptionData, l2_Nova_i) != 0)
		{
			return (int)(char)(intptr_t)nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(this->nova_Nova_String_Nova_chars), exceptionData, l2_Nova_i) - (char)(intptr_t)nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(nova_Nova_String_Nova_other->nova_Nova_String_Nova_chars), exceptionData, l2_Nova_i);
		}
	}
	return this->nova_Nova_String_Nova_count - nova_Nova_String_Nova_other->nova_Nova_String_Nova_count;
}

nova_Nova_String* nova_Nova_String_0_Nova_toString(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this;
}

nova_datastruct_list_Nova_CharArray* generated1(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	char* l1_Nova_temp = (char*)nova_null;
	
	l1_Nova_temp = (char*)NOVA_MALLOC(sizeof(nova_primitive_number_Nova_Char) * 4);
	l1_Nova_temp[0] = ' ';
	l1_Nova_temp[1] = '\t';
	l1_Nova_temp[2] = '\n';
	l1_Nova_temp[3] = '\r';
	return nova_datastruct_list_Nova_CharArray_2_Nova_CharArray(0, exceptionData, l1_Nova_temp, 4);
}

void nova_Nova_String_Nova_testLambda32(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_Nova_String_Nova_c, int nova_Nova_String_Nova_i, nova_datastruct_list_Nova_CharArray* nova_Nova_String_Nova__3, Context1* context)
{
	(*context->nova_Nova_String_Nova_hash) = 31 * (*context->nova_Nova_String_Nova_hash) + (int)nova_Nova_String_Nova_c;
}

long_long nova_Nova_String_Accessor_Nova_hashCodeLong(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	long_long l1_Nova_hash = 0;
	Context1 contextArg36 = 
	{
		&l1_Nova_hash,
	};
	
	l1_Nova_hash = (long_long)(0);
	nova_datastruct_list_Nova_List_virtual0_Nova_forEach((nova_datastruct_list_Nova_List*)(this->nova_Nova_String_Nova_chars), exceptionData, (nova_datastruct_list_Nova_List_closure3_Nova_func)&nova_Nova_String_Nova_testLambda32, this, &contextArg36);
	return l1_Nova_hash;
}


void nova_Nova_String_Nova_super(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_Nova_String_Nova_count = 0;
	this->nova_Nova_String_Nova_chars = (nova_datastruct_list_Nova_CharArray*)nova_null;
}

nova_Nova_String* nova_Nova_String_virtual1_Nova_concat(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_str)
{
	return this->vtable->nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(this), exceptionData, nova_Nova_String_Nova_str);
}

