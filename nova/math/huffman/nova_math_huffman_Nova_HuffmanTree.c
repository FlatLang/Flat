#include <precompiled.h>
#include <nova/math/huffman/nova_math_huffman_Nova_HuffmanTree.h>



nova_math_huffman_Extension_VTable_HuffmanTree nova_math_huffman_Extension_VTable_HuffmanTree_val =
{
	{
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
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void nova_math_huffman_Nova_HuffmanTree_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_math_huffman_Nova_HuffmanTree* nova_math_huffman_Nova_HuffmanTree_Nova_construct(nova_math_huffman_Nova_HuffmanTree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_math_huffman_Nova_HuffmanTree, this,);
	this->vtable = &nova_math_huffman_Extension_VTable_HuffmanTree_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_Nova_Tree_Nova_super((nova_datastruct_Nova_Tree*)this, exceptionData);
	nova_math_huffman_Nova_HuffmanTree_0_Nova_super(this, exceptionData);
	
	{
		nova_math_huffman_Nova_HuffmanTree_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_math_huffman_Nova_HuffmanTree_Nova_destroy(nova_math_huffman_Nova_HuffmanTree** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_math_huffman_Nova_HuffmanTree_2_Nova_this(nova_math_huffman_Nova_HuffmanTree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_math_huffman_Nova_HuffmanTree_0_Nova_super(nova_math_huffman_Nova_HuffmanTree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

