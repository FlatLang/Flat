function __copyStaticFunctions() {
	[].slice.call(arguments).forEach(f =>
		Object.getOwnPropertyNames(f)
		  .filter(d => !f.prototype.hasOwnProperty(d))
		  .forEach(d => f.prototype[d] = f[d])
	);
}

function __copyPrototype(f, ...g) {
	let v = f.prototype;
	g.map(a => a.prototype)
	  .forEach(p => Object.getOwnPropertyNames(p)
		  .filter(d => !v.hasOwnProperty(d))
		  .forEach(d => v[d] = p[d])
	);
	return __copyPrototype;
}

var flat_null = undefined;

class FlatObject {
	static construct() {
		let __value = new FlatObject();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatObject.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	equals(another) {
		return FlatObject.referenceEquals(this, another);
	}

	toString() {
		return FlatString.construct5("{").plus0((this.accessor__js_class().accessor_name()).plus0(FlatString.construct5(" @").plus0((this.accessor_hashCode()).plus0(FlatString.construct5("}")))));
	}

	toJson(format, tab) {
		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		return FlatString.construct5("{}");
	}

	static __init() {
		let self = this;

		return self;
	}

	async __chainAsync(funcName, args) {
		await this[funcName].apply(this, args);
		return this;
	}

	__chain(funcName, args) {
		this[funcName].apply(this, args);
		return this;
	}

	async __callExtensionAsync(func, args) {
		return await func.apply(this, args);
	}

	__callExtension(func, args) {
		return func.apply(this, args);
	}

	async __callExtensionChainAsync(func, args) {
		await func.apply(this, args);
		return this;
	}

	__callExtensionChain(func, args) {
		func.apply(this, args);
		return this;
	}

	static referenceEquals(one, two) {
		return one === two;
	}

	static generated103(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated123(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		return FlatArray.construct1(temp, 12);
	}

	static accessor__js_class() {
		return typeof FlatObject.__lazy_accessor__js_class !== 'undefined' ? FlatObject.__lazy_accessor__js_class : (FlatObject.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/Object"), false, flat_null, FlatArray.construct(), FlatObject.generated103(Field.construct(FlatString.construct5("hashCodeLong")), Field.construct(FlatString.construct5("hashCode"))), FlatObject.generated123(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("__chainAsync")), Function.construct(FlatString.construct5("__chain")), Function.construct(FlatString.construct5("__callExtensionAsync")), Function.construct(FlatString.construct5("__callExtension")), Function.construct(FlatString.construct5("__callExtensionChainAsync")), Function.construct(FlatString.construct5("__callExtensionChain")), Function.construct(FlatString.construct5("referenceEquals"))), this);
			})());
	}

	accessor_hashCodeLong() {
		return 0;
	}

	accessor_hashCode() {
		return FlatString.construct5("No hashcode available");
	}

	static __assignments() {
	}
}

class App extends FlatObject {
	static name = flat_null;
	static version = flat_null;
	static description = flat_null;
	static author = flat_null;
	static license = flat_null;
	static defaultTarget = flat_null;

	static construct() {
		let __value = new App();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		App.__assignments.apply(__value, [].slice.call(arguments));
		__value = App.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated14(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof App.__lazy_accessor__js_class !== 'undefined' ? App.__lazy_accessor__js_class : (App.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/App"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), App.generated14(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class PlusEqualsOperator {
	static generated129(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof PlusEqualsOperator.__lazy_accessor__js_class !== 'undefined' ? PlusEqualsOperator.__lazy_accessor__js_class : (PlusEqualsOperator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/operators/PlusEqualsOperator"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), PlusEqualsOperator.generated129(Function.construct(FlatString.construct5("plusEquals"))), this);
			})());
	}
}

class PlusOperator {
	static generated114(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof PlusOperator.__lazy_accessor__js_class !== 'undefined' ? PlusOperator.__lazy_accessor__js_class : (PlusOperator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/operators/PlusOperator"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), PlusOperator.generated114(Function.construct(FlatString.construct5("plus"))), this);
			})());
	}
}

class OrderedList {
	findIndex(condition, defaultReturnValue) {
		let flat_local_0 = flat_null;
		defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
		let i = -1;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (condition(element, ++i, this)) {
				return i;
			}
		}
		return defaultReturnValue;
	}

	indexOf(element, defaultReturnValue) {
		let flat_local_0 = flat_null;
		defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let e = flat_null;
			e = flat_local_0.accessor_stepNext();
			if (element === flat_null && e === flat_null || element !== flat_null && element.equals(e)) {
				return i;
			}
			i++;
		}
		return defaultReturnValue;
	}

	static generated130(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated139(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof OrderedList.__lazy_accessor__js_class !== 'undefined' ? OrderedList.__lazy_accessor__js_class : (OrderedList.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/OrderedList"), true, FlatObject.accessor__js_class(), OrderedList.generated130(List.accessor__js_class()), FlatArray.construct(), OrderedList.generated139(Function.construct(FlatString.construct5("findIndex")), Function.construct(FlatString.construct5("indexOf"))), this);
			})());
	}
}

class List {
	toArray() {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			array.add0(value);
		}
		return array;
	}

	contains0(value) {
		let self = this;

		return this.any0((_x, _i, _list) => {
				return _x === flat_null && value === flat_null || _x !== flat_null && _x.equals(value);
		});
	}

	containsAny(values) {
		let self = this;

		return this.any0((_x, _i, _list) => {
				return values.contains0(_x);
		});
	}

	forEach1(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			func(value, i++, this);
		}
		return this;
	}

	async forEachParallel1(func, maxParallel, onAfterChunk) {
		let self = this;

		maxParallel = typeof maxParallel === 'undefined' ? -1 : maxParallel;
		onAfterChunk = typeof onAfterChunk === 'undefined' ? (_count) => {
		} : onAfterChunk;
		(await Async.all1(this.toArray(), async (_1, _2) => {
					(await func(_1, _2, this));
					return flat_null;
		}, maxParallel, onAfterChunk));
		return this;
	}

	async forEachAsync1(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			(await func(element, i++, this));
		}
		return this;
	}

	map1(mapFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct0(this.accessor_count(), undefined);
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			array.add0(mapFunc(element, i++, this));
		}
		return array;
	}

	async mapParallel1(mapFunc, maxParallel, onAfterChunk) {
		let self = this;

		maxParallel = typeof maxParallel === 'undefined' ? -1 : maxParallel;
		onAfterChunk = typeof onAfterChunk === 'undefined' ? (_count) => {
		} : onAfterChunk;
		return (await Async.all1(this.toArray(), async (_1, _2) => {
					return (await mapFunc(_1, _2, this));
		}, maxParallel, onAfterChunk));
	}

	async mapAsync1(mapFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct0(this.accessor_count(), undefined);
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			array.add0((await mapFunc(element, i++, this)));
		}
		return array;
	}

	mapNotNull1(mapFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			let value = mapFunc(element, i++, this);
			if (value !== flat_null) {
				array.add0(value);
			}
		}
		return array;
	}

	async mapNotNullParallel1(mapFunc, maxParallel, onAfterChunk) {
		let self = this;

		maxParallel = typeof maxParallel === 'undefined' ? -1 : maxParallel;
		onAfterChunk = typeof onAfterChunk === 'undefined' ? (_count) => {
		} : onAfterChunk;
		return (await Async.all1(this.toArray(), async (_1, _2) => {
					return (await mapFunc(_1, _2, this));
		}, maxParallel, onAfterChunk)).filterNull();
	}

	async mapNotNullAsync1(mapFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			let value = (await mapFunc(element, i++, this));
			if (value !== flat_null) {
				array.add0(value);
			}
		}
		return array;
	}

	flatMap1(mapFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			array.addAll(mapFunc(element, i++, this));
		}
		return array;
	}

	async flatMapAsync1(mapFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			array.addAll((await mapFunc(element, i++, this)));
		}
		return array;
	}

	async flatMapParallel1(mapFunc, maxParallel, onAfterChunk) {
		let self = this;

		maxParallel = typeof maxParallel === 'undefined' ? -1 : maxParallel;
		onAfterChunk = typeof onAfterChunk === 'undefined' ? (_count) => {
		} : onAfterChunk;
		let array = FlatArray.construct();
		(await Async.all1(this.toArray(), async (_1, _2) => {
					return (await mapFunc(_1, _2, this));
		}, maxParallel, onAfterChunk)).forEach1((_x, _i, _array) => {
				array.addAll(_x);
		});
		return array;
	}

	any0(anyFunc) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (anyFunc(element, i++, this)) {
				return true;
			}
		}
		return false;
	}

	async anyAsync(anyFunc) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if ((await anyFunc(element, i++, this))) {
				return true;
			}
		}
		return false;
	}

	all0(allFunc, stopOnContradiction) {
		let flat_local_0 = flat_null;
		stopOnContradiction = typeof stopOnContradiction === 'undefined' ? true : stopOnContradiction;
		let i = 0;
		let contradiction = false;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (!allFunc(element, i++, this)) {
				if (stopOnContradiction) {
					return false;
				}
				contradiction = true;
			}
		}
		return !contradiction;
	}

	async allAsync(allFunc, stopOnContradiction) {
		let flat_local_0 = flat_null;
		stopOnContradiction = typeof stopOnContradiction === 'undefined' ? true : stopOnContradiction;
		let i = 0;
		let contradiction = false;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (!(await allFunc(element, i++, this))) {
				if (stopOnContradiction) {
					return false;
				}
				contradiction = true;
			}
		}
		return !contradiction;
	}

	none(func, stopOnContradiction) {
		let flat_local_0 = flat_null;
		stopOnContradiction = typeof stopOnContradiction === 'undefined' ? true : stopOnContradiction;
		let i = 0;
		let contradiction = false;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (func(element, i++, this)) {
				if (stopOnContradiction) {
					return false;
				}
				contradiction = true;
			}
		}
		return !contradiction;
	}

	async noneAsync(func, stopOnContradiction) {
		let flat_local_0 = flat_null;
		stopOnContradiction = typeof stopOnContradiction === 'undefined' ? true : stopOnContradiction;
		let i = 0;
		let contradiction = false;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if ((await func(element, i++, this))) {
				if (stopOnContradiction) {
					return false;
				}
				contradiction = true;
			}
		}
		return !contradiction;
	}

	async noneParallel(func, maxParallel, onAfterChunk) {
		let self = this;

		let flat_local_0 = flat_null;
		maxParallel = typeof maxParallel === 'undefined' ? -1 : maxParallel;
		onAfterChunk = typeof onAfterChunk === 'undefined' ? (_count) => {
		} : onAfterChunk;
		let i = 0;
		let contradiction = false;
		let checked = (await Async.all1(this.toArray(), async (_1, _2) => {
					return Bool.construct((await func(_1, _2, this)));
		}, maxParallel, onAfterChunk));
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			let flat_local_1 = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (((flat_local_1 = (checked.get(i++))) !== flat_null ? (flat_local_1.value) : false) === true) {
				return false;
			}
		}
		return !contradiction;
	}

	filter1(filterFunc) {
		let flat_local_0 = flat_null;
		let list = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			if (filterFunc(value, i++, this)) {
				list.add0(value);
			}
		}
		return list;
	}

	async filterParallel1(filterFunc, maxParallel, onAfterChunk) {
		let self = this;

		let flat_local_0 = flat_null;
		maxParallel = typeof maxParallel === 'undefined' ? -1 : maxParallel;
		onAfterChunk = typeof onAfterChunk === 'undefined' ? (_count) => {
		} : onAfterChunk;
		let list = FlatArray.construct();
		let promiseArray = FlatArray.construct0(this.accessor_count(), undefined);
		let filtered = (await Async.all1(this.toArray(), async (_1, _2) => {
					return Bool.construct((await filterFunc(_1, _2, this)));
		}, maxParallel, onAfterChunk));
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			let flat_local_1 = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (((flat_local_1 = (filtered.get(i++))) !== flat_null ? (flat_local_1.value) : false) === true) {
				list.add0(element);
			}
		}
		return list;
	}

	async filterAsync1(filterFunc) {
		let flat_local_0 = flat_null;
		let list = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if ((await filterFunc(element, i++, this))) {
				list.add0(element);
			}
		}
		return list;
	}

	filterNot(filterFunc) {
		let flat_local_0 = flat_null;
		let list = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			if (!filterFunc(value, i++, this)) {
				list.add0(value);
			}
		}
		return list;
	}

	filterNull() {
		let self = this;

		return this.filter1((_x, _i, _list) => {
				return _x !== flat_null;
		});
	}

	uniqueBy1(prop) {
		let flat_local_0 = flat_null;
		let list = FlatArray.construct();
		let values = FlatArray.construct();
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			let x = prop(value);
			if (!values.contains0(x)) {
				list.add0(value);
				values.add0(x);
			}
		}
		return list;
	}

	unique() {
		let flat_local_0 = flat_null;
		let list = FlatArray.construct();
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			if (!list.contains0(value)) {
				list.add0(value);
			}
		}
		return list;
	}

	filterAtIndex(index, count) {
		let self = this;

		count = typeof count === 'undefined' ? 1 : count;
		return this.filter1((_x, _i, _list) => {
				return _i < index && _i >= index + count;
		});
	}

	takeWhile1(filterFunc) {
		let flat_local_0 = flat_null;
		let list = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			if (!filterFunc(value, i++, this)) {
				return list;
			}
			list.add0(value);
		}
		return list;
	}

	take(howMany) {
		let flat_local_0 = flat_null;
		let list = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			if (i++ >= howMany) {
				return list;
			}
			list.add0(value);
		}
		return list;
	}

	takeLast(howMany) {
		let flat_local_0 = flat_null;
		let list = FlatArray.construct();
		let startIndex = FlatMath.max1(0, this.accessor_count() - howMany);
		if (startIndex === this.accessor_count()) {
			return list;
		}
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			if (i++ >= startIndex) {
				list.add0(value);
			}
		}
		return list;
	}

	skipLast(howMany) {
		return this.take(this.accessor_count() - howMany);
	}

	skip(howMany) {
		let flat_local_0 = flat_null;
		let list = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			if (i++ >= howMany) {
				list.add0(value);
			}
		}
		return list;
	}

	findOne(func) {
		let value = flat_null;
		let flat_local_0 = flat_null;
		func = typeof func === 'undefined' ? (_x, _i, _list) => {
			return true;
		} : func;
		let i = 0;
		let matched = false;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (func(element, i++, this)) {
				if (matched) {
					return flat_null;
				}
				matched = true;
				value = element;
			}
		}
		return value;
	}

	firstOr(_js_default, func) {
		let flat_local_0 = flat_null;
		func = typeof func === 'undefined' ? (_x, _i, _list) => {
			return true;
		} : func;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (func(element, i++, this)) {
				return element;
			}
		}
		return _js_default;
	}

	async firstOrAsync(_js_default, func) {
		let flat_local_0 = flat_null;
		func = typeof func === 'undefined' ? (_x, _i, _list) => {
			return true;
		} : func;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if ((await func(element, i++, this))) {
				return element;
			}
		}
		return _js_default;
	}

	firstOrNull(func) {
		func = typeof func === 'undefined' ? (_x, _i, _list) => {
			return true;
		} : func;
		return this.firstOr(flat_null, func);
	}

	async firstOrNullAsync(func) {
		func = typeof func === 'undefined' ? (_x, _i, _list) => {
			return true;
		} : func;
		return (await this.firstOrAsync(flat_null, func));
	}

	firstWhereOrThrow(e, func) {
		let flat_local_0 = flat_null;
		func = typeof func === 'undefined' ? (_x, _i, _list) => {
			return flat_null;
		} : func;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			let value = flat_null;
			element = flat_local_0.accessor_stepNext();
			if ((value = func(element, i++, this)) !== flat_null) {
				return value;
			}
		}
		throw e;
	}

	firstOrThrow(e, func) {
		let flat_local_0 = flat_null;
		func = typeof func === 'undefined' ? (_x, _i, _list) => {
			return true;
		} : func;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (func(element, i++, this)) {
				return element;
			}
		}
		throw e;
	}

	firstWhere0(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (func(element, i++, this)) {
				return element;
			}
		}
		return flat_null;
	}

	lastWhere1(func) {
		return this.reverse().firstWhere0(func);
	}

	firstNonNull(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			let value = func(element, i++, this);
			if ((value) !== flat_null) {
				return value;
			}
		}
		return flat_null;
	}

	lastNonNull1(func) {
		return this.reverse().firstNonNull(func);
	}

	async firstNonNullAsync(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			let value = (await func(element, i++, this));
			if ((value) !== flat_null) {
				return value;
			}
		}
		return flat_null;
	}

	zip(other, zipper) {
		let i1 = this.accessor_iterator();
		let i2 = other.accessor_iterator();
		let array = FlatArray.construct();
		let i = 0;
		while (i1.accessor_hasNext() && i2.accessor_hasNext()) {
			array.add0(zipper(i1.accessor_stepNext(), i2.accessor_stepNext(), i++, this, other));
		}
		return array;
	}

	async zipAsync(other, zipper) {
		let i1 = this.accessor_iterator();
		let i2 = other.accessor_iterator();
		let array = FlatArray.construct();
		let i = 0;
		while (i1.accessor_hasNext() && i2.accessor_hasNext()) {
			array.add0((await zipper(i1.accessor_stepNext(), i2.accessor_stepNext(), i++, this, other)));
		}
		return array;
	}

	sum0(func) {
		let flat_local_0 = flat_null;
		let sum = 0;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			sum += func(element, i++, this);
		}
		return sum;
	}

	sum1(func) {
		let flat_local_0 = flat_null;
		let sum = 0;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			sum += func(element, i++, this);
		}
		return sum;
	}

	min(func) {
		let flat_local_0 = flat_null;
		let min = FlatLong.MAX_VALUE;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			min = FlatMath.min0(min, func(element, i++, this));
		}
		return min;
	}

	max(func) {
		let flat_local_0 = flat_null;
		let max = FlatLong.MIN_VALUE;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			max = FlatMath.max0(max, func(element, i++, this));
		}
		return max;
	}

	howMany(func) {
		let flat_local_0 = flat_null;
		let counter = 0;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (func(element, i++, this)) {
				counter++;
			}
		}
		return counter;
	}

	reduce(func, initialValue) {
		let flat_local_0 = flat_null;
		let value = initialValue;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			value = func(value, element, i++, this);
		}
		return value;
	}

	split0(delimiter) {
		let self = this;

		return this.split1((_x, _i, _list) => {
				return _x.equals(delimiter);
		});
	}

	split1(delimiterFunc) {
		let flat_local_0 = flat_null;
		let list = FlatArray.construct();
		let current = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (delimiterFunc(element, i++, this)) {
				list.add0(current);
				current = FlatArray.construct();
			} else {
				current.add0(element);
			}
		}
		if (this.accessor_count() > 0 && (this.accessor_count() > 1 || current.accessor_isNotEmpty())) {
			list.add0(current);
		}
		return list;
	}

	join(delimiter, maxOutputCount, ellipsePosition, ellipse) {
		let self = this;

		delimiter = typeof delimiter === 'undefined' ? FlatString.construct5("") : delimiter;
		maxOutputCount = typeof maxOutputCount === 'undefined' ? this.accessor_count() : maxOutputCount;
		ellipsePosition = typeof ellipsePosition === 'undefined' ? ~~(maxOutputCount / 2) : ellipsePosition;
		ellipse = typeof ellipse === 'undefined' ? FlatString.construct5("...") : ellipse;
		maxOutputCount = maxOutputCount > this.accessor_count() ? this.accessor_count() : maxOutputCount;
		maxOutputCount = maxOutputCount < 0 ? 0 : maxOutputCount;
		if (maxOutputCount < this.accessor_count()) {
			let flat_local_0 = flat_null;
			if (maxOutputCount === 0 && this.accessor_count() > 0) {
				return ellipse;
			}
			let str = FlatString.construct5("");
			let i = 0;
			flat_local_0 = (this).accessor_iterator();
			while (flat_local_0.accessor_hasNext()) {
				let value = flat_null;
				value = flat_local_0.accessor_stepNext();
				if (i < ellipsePosition) {
					str = str.concat(value.toString().plus0(delimiter));
				} else if (i === ellipsePosition) {
					str = str.concat(ellipse);
				} else if (i >= this.accessor_count() - (maxOutputCount - ellipsePosition)) {
					str = str.concat(delimiter.plus0(value.toString()));
				}
				i++;
			}
			return str;
		} else {
			return this.reduce((_acc, _x, _i, _list) => {
					return _acc.plus0((_i > 0 ? delimiter : FlatString.construct5("")).plus0(_x.toString()));
				}, FlatString.construct5(""));
		}
	}

	stitch(delimiter) {
		let self = this;

		delimiter = typeof delimiter === 'undefined' ? FlatString.construct5("") : delimiter;
		return this.reduce((_acc, _x, _i, _list) => {
				return _acc.plus0(_x.toString().plus0(delimiter));
			}, FlatString.construct5(""));
	}

	stitchFront(delimiter) {
		let self = this;

		delimiter = typeof delimiter === 'undefined' ? FlatString.construct5("") : delimiter;
		return this.reduce((_acc, _x, _i, _list) => {
				return _acc.plus0(delimiter.plus0(_x.toString()));
			}, FlatString.construct5(""));
	}

	toEnglish(conjunction, oxford) {
		oxford = typeof oxford === 'undefined' ? true : oxford;
		return this.accessor_count() > 1 ? this.take(this.accessor_count() - 1).join(FlatString.construct5(", "), undefined, undefined, undefined).plus0((oxford && this.accessor_count() > 2 ? FlatString.construct5(", ") : FlatString.construct5(" ")).plus0((conjunction).plus0(FlatString.construct5(" ")).plus0(this.skip(this.accessor_count() - 1).accessor_first().toString()))) : (this.accessor_first()).toString().plus0(FlatString.construct5(""));
	}

	toString() {
		return this.accessor__js_class().location.plus0(FlatString.construct5(" {").plus0(this.join(FlatString.construct5(", "), undefined, undefined, undefined).plus0(FlatString.construct5("}"))));
	}

	toJson(format, tab) {
		let self = this;

		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		if (format) {
			let flat_local_0 = flat_null;
			let values = this.map1((_x, _i, _list) => {
					return _x.toJson(format, tab);
			});
			let sum = 0;
			flat_local_0 = (values).accessor_iterator();
			while (flat_local_0.accessor_hasNext()) {
				let str = flat_null;
				str = flat_local_0.accessor_stepNext();
				sum += str.count;
			}
			if (values.accessor_count() <= 1 || sum < 50) {
				return FlatString.construct5("[").plus0((values.join(FlatString.construct5(", "), undefined, undefined, undefined)).plus0(FlatString.construct5("]")));
			}
			return FlatString.construct5("[\n").plus0((values.join(FlatString.construct5(",\n"), undefined, undefined, undefined).indent(undefined, tab, undefined)).plus0(FlatString.construct5("\n]")));
		} else {
			let values = this.map1((_x, _i, _list) => {
					return _x.toJson(undefined, undefined);
			}).join(FlatString.construct5(","), undefined, undefined, undefined);
			return FlatString.construct5("[").plus0((values).plus0(FlatString.construct5("]")));
		}
	}

	equals(another) {
		if (!((another) !== flat_null)) {
			return false;
		}
		if (!another.accessor__js_class().isOfType(List.accessor__js_class())) {
			return false;
		}
		let otherList = another;
		if (otherList.accessor_count() !== this.accessor_count()) {
			return false;
		}
		let iteratorValue = this.accessor_iterator();
		let otherIteratorValue = otherList.accessor_iterator();
		while (iteratorValue.accessor_hasNext()) {
			if (!iteratorValue.accessor_stepNext().equals(otherIteratorValue.accessor_stepNext())) {
				return false;
			}
		}
		return true;
	}

	static generated81(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated84(value0, value1, value2, value3, value4, value5) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		return FlatArray.construct1(temp, 6);
	}

	static generated192(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44, value45, value46, value47, value48, value49, value50, value51, value52, value53, value54, value55, value56, value57, value58, value59, value60, value61, value62, value63, value64) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		temp[41] = value41;
		temp[42] = value42;
		temp[43] = value43;
		temp[44] = value44;
		temp[45] = value45;
		temp[46] = value46;
		temp[47] = value47;
		temp[48] = value48;
		temp[49] = value49;
		temp[50] = value50;
		temp[51] = value51;
		temp[52] = value52;
		temp[53] = value53;
		temp[54] = value54;
		temp[55] = value55;
		temp[56] = value56;
		temp[57] = value57;
		temp[58] = value58;
		temp[59] = value59;
		temp[60] = value60;
		temp[61] = value61;
		temp[62] = value62;
		temp[63] = value63;
		temp[64] = value64;
		return FlatArray.construct1(temp, 65);
	}

	accessor_count() {
		return 0;
	}

	mutator_count(value) {
		return value;
	}

	accessor_iterator() {
		return flat_null;
	}

	mutator_iterator(value) {
		return value;
	}

	accessor_first() {
		return flat_null;
	}

	mutator_first(value) {
		return value;
	}

	accessor_last() {
		return flat_null;
	}

	mutator_last(value) {
		return value;
	}

	static accessor__js_class() {
		return typeof List.__lazy_accessor__js_class !== 'undefined' ? List.__lazy_accessor__js_class : (List.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/List"), true, FlatObject.accessor__js_class(), List.generated81(Iterable.accessor__js_class()), List.generated84(Field.construct(FlatString.construct5("count")), Field.construct(FlatString.construct5("iterator")), Field.construct(FlatString.construct5("first")), Field.construct(FlatString.construct5("last")), Field.construct(FlatString.construct5("isEmpty")), Field.construct(FlatString.construct5("isNotEmpty"))), List.generated192(Function.construct(FlatString.construct5("toArray")), Function.construct(FlatString.construct5("contains")), Function.construct(FlatString.construct5("containsAny")), Function.construct(FlatString.construct5("forEach")), Function.construct(FlatString.construct5("forEachParallel")), Function.construct(FlatString.construct5("forEachAsync")), Function.construct(FlatString.construct5("map")), Function.construct(FlatString.construct5("mapParallel")), Function.construct(FlatString.construct5("mapAsync")), Function.construct(FlatString.construct5("mapNotNull")), Function.construct(FlatString.construct5("mapNotNullParallel")), Function.construct(FlatString.construct5("mapNotNullAsync")), Function.construct(FlatString.construct5("flatMap")), Function.construct(FlatString.construct5("flatMapAsync")), Function.construct(FlatString.construct5("flatMapParallel")), Function.construct(FlatString.construct5("any")), Function.construct(FlatString.construct5("anyAsync")), Function.construct(FlatString.construct5("all")), Function.construct(FlatString.construct5("allAsync")), Function.construct(FlatString.construct5("none")), Function.construct(FlatString.construct5("noneAsync")), Function.construct(FlatString.construct5("noneParallel")), Function.construct(FlatString.construct5("filter")), Function.construct(FlatString.construct5("filterParallel")), Function.construct(FlatString.construct5("filterAsync")), Function.construct(FlatString.construct5("filterNot")), Function.construct(FlatString.construct5("filterNull")), Function.construct(FlatString.construct5("uniqueBy")), Function.construct(FlatString.construct5("unique")), Function.construct(FlatString.construct5("filterAtIndex")), Function.construct(FlatString.construct5("takeWhile")), Function.construct(FlatString.construct5("take")), Function.construct(FlatString.construct5("takeLast")), Function.construct(FlatString.construct5("skipLast")), Function.construct(FlatString.construct5("skip")), Function.construct(FlatString.construct5("findOne")), Function.construct(FlatString.construct5("firstOr")), Function.construct(FlatString.construct5("firstOrAsync")), Function.construct(FlatString.construct5("firstOrNull")), Function.construct(FlatString.construct5("firstOrNullAsync")), Function.construct(FlatString.construct5("firstWhereOrThrow")), Function.construct(FlatString.construct5("firstOrThrow")), Function.construct(FlatString.construct5("firstWhere")), Function.construct(FlatString.construct5("lastWhere")), Function.construct(FlatString.construct5("firstNonNull")), Function.construct(FlatString.construct5("lastNonNull")), Function.construct(FlatString.construct5("firstNonNullAsync")), Function.construct(FlatString.construct5("zip")), Function.construct(FlatString.construct5("zipAsync")), Function.construct(FlatString.construct5("sum")), Function.construct(FlatString.construct5("sum")), Function.construct(FlatString.construct5("min")), Function.construct(FlatString.construct5("max")), Function.construct(FlatString.construct5("howMany")), Function.construct(FlatString.construct5("reverse")), Function.construct(FlatString.construct5("reduce")), Function.construct(FlatString.construct5("split")), Function.construct(FlatString.construct5("split")), Function.construct(FlatString.construct5("join")), Function.construct(FlatString.construct5("stitch")), Function.construct(FlatString.construct5("stitchFront")), Function.construct(FlatString.construct5("toEnglish")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("equals"))), this);
			})());
	}

	accessor_isEmpty() {
		return this.accessor_count() === 0;
	}

	accessor_isNotEmpty() {
		return !this.accessor_isEmpty();
	}
}

class FlatArray extends FlatObject {
	capacity = 0;
	count = 0;
	position = 0;
	data = flat_null;

	static construct() {
		let __value = new FlatArray();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatArray.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatArray.__init.call(__value);

		return __value;
	}

	static construct0(count, initialCapacity) {
		let __value = new FlatArray();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatArray.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatArray.__init0.call(__value, count, initialCapacity);

		return __value;
	}

	static construct1(data, count) {
		let __value = new FlatArray();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatArray.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatArray.__init1.call(__value, data, count);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		self = FlatArray.__init0.call(this, 0);
		this.increaseSize1(10);
		return self;
	}

	static __init0(count, initialCapacity) {
		let self = this;

		initialCapacity = typeof initialCapacity === 'undefined' ? count : initialCapacity;
		this.position = 0;
		this.capacity = 0;
		this.increaseSize1(initialCapacity);
		this.mutator_count(count);
		this.data = new Array(count).fill(flat_null);
		return self;
	}

	static __init1(data, count) {
		let self = this;

		this.data = data;
		this.mutator_count(count);
		this.capacity = count;
		this.position = count;
		return self;
	}

	getLast(offset) {
		offset = typeof offset === 'undefined' ? 0 : offset;
		return this.accessor_count() > offset ? this.get(this.accessor_count() - 1 - offset) : flat_null;
	}

	fillRemaining(value) {
		while (this.accessor_count() < this.capacity) {
			this.add0(value);
		}
		return value;
	}

	plus0(other) {
		return this.clone().plusEquals0(other);
	}

	plusEquals0(other) {
		return this.__chain('addAll', [other]);
	}

	addAll(data) {
		let flat_local_0 = flat_null;
		flat_local_0 = (data).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let d = flat_null;
			d = flat_local_0.accessor_stepNext();
			this.add0(d);
		}
		return data;
	}

	addUnique(element) {
		if (!this.contains0(element)) {
			this.add0(element);
		}
		return element;
	}

	add0(element) {
		if (this.position >= this.capacity) {
			this.increaseSize0();
		}
		this.data[this.position++] = element;
		this.mutator_count(FlatMath.max1(this.position, this.accessor_count()));
		return element;
	}

	add1(index, element) {
		if (index >= this.capacity) {
			this.increaseSize1(index + 1);
		}
		this.add0(flat_null);
		this.shiftRight(index, this.position);
		this.data[index] = element;
		if (index >= this.position - 1) {
			this.position = index + 1;
		}
		this.mutator_count(FlatMath.max1(this.position, this.accessor_count()));
		return element;
	}

	unshift(element) {
		this.add1(0, element);
		return element;
	}

	shift() {
		return this.removeAtIndex0(0);
	}

	removeLast() {
		return this.removeAtIndex0(this.accessor_count() - 1);
	}

	removeWhere(func) {
		let removed = FlatArray.construct();
		{
			let i = this.accessor_count() - 1;
			for (; i >= 0; i--) {
				if (!func(this.get(i), i, this)) {
					continue;
				}
				removed.add0(this.removeAtIndex0(i));
			}
		}
		return removed;
	}

	remove0(index) {
		let element = this.data[index];
		this.shiftLeft(index + 1, this.position--);
		this.mutator_count(this.accessor_count() - 1);
		return element;
	}

	removeAtIndex0(index) {
		return this.removeAtIndex1(index, 1).accessor_first();
	}

	removeAtIndex1(index, count) {
		count = typeof count === 'undefined' ? 1 : count;
		let output = FlatArray.construct();
		{
			let i = 0;
			for (; i < count; i++) {
				output.add0(this.remove0(index));
			}
		}
		return output;
	}

	removeFirstWhere(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (func(element, i, this)) {
				return this.removeAtIndex0(i);
			}
			i++;
		}
		return flat_null;
	}

	remove1(element) {
		let index = this.indexOf(element, undefined);
		if (index >= 0) {
			this.shiftLeft(index + 1, this.position--);
			this.mutator_count(this.accessor_count() - 1);
			return element;
		}
		return flat_null;
	}

	replace(element, other) {
		let index = this.indexOf(element, undefined);
		if (index >= 0) {
			this.set(index, other);
			return other;
		}
		return flat_null;
	}

	replaceAt(index, element) {
		this.set(index, element);
		return element;
	}

	replaceFirst(element, offset) {
		offset = typeof offset === 'undefined' ? 0 : offset;
		return this.replaceAt(offset, element);
	}

	replaceLast(element, offset) {
		offset = typeof offset === 'undefined' ? 0 : offset;
		return this.replaceAt(this.accessor_count() - offset - 1, element);
	}

	shiftRight(left, right) {
		let i = right - 1;
		while (i > left) {
			this.data[i] = this.data[i - 1];
			i--;
		}
		this.data[left] = flat_null;
		return this;
	}

	shiftLeft(left, right) {
		{
			let i = left;
			for (; i < right; i++) {
				this.data[i - 1] = this.data[i];
			}
		}
		this.data[right - 1] = flat_null;
		return this;
	}

	swap(index1, index2) {
		let temp = this.data[index1];
		this.data[index1] = this.data[index2];
		this.data[index2] = temp;
		return this;
	}

	increaseSize0() {
		this.increaseSize1(this.capacity + 3);
		return this;
	}

	increaseSize1(count) {
		this.capacity = count;
		return this;
	}

	clone() {
		let self = this;

		return this.filter1((_x, _i, _array) => {
				return true;
		});
	}

	toArray() {
		return this.clone();
	}

	clear() {
		let copy = FlatArray.construct1(this.data, this.accessor_count());
		this.data = new Array();
		this.position = 0;
		this.capacity = 0;
		this.mutator_count(0);
		return copy;
	}

	lastWhere1(func) {
		{
			let i = this.accessor_count() - 1;
			for (; i >= 0; i--) {
				let element = this.get(i);
				if (func(element, i, this)) {
					return element;
				}
			}
		}
		return flat_null;
	}

	lastNonNull1(mapFunc) {
		{
			let i = this.accessor_count() - 1;
			for (; i >= 0; i--) {
				let value = flat_null;
				let element = this.get(i);
				if ((value = mapFunc(element, i, this)) !== flat_null) {
					return value;
				}
			}
		}
		return flat_null;
	}

	map1(mapFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct0(this.accessor_count(), undefined);
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			array.add0(mapFunc(element, i++, this));
		}
		return array;
	}

	async mapParallel1(mapFunc, maxParallel, onAfterChunk) {
		let self = this;

		maxParallel = typeof maxParallel === 'undefined' ? -1 : maxParallel;
		onAfterChunk = typeof onAfterChunk === 'undefined' ? (_count) => {
		} : onAfterChunk;
		return (await Async.all1(this, async (_1, _2) => {
					return (await mapFunc(_1, _2, this));
		}, maxParallel, onAfterChunk));
	}

	async mapAsync1(mapFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct0(this.accessor_count(), undefined);
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			array.add0((await mapFunc(element, i++, this)));
		}
		return array;
	}

	mapNotNull1(mapFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			let value = mapFunc(element, i++, this);
			if (value !== flat_null) {
				array.add0(value);
			}
		}
		return array;
	}

	async mapNotNullParallel1(mapFunc, maxParallel, onAfterChunk) {
		let self = this;

		maxParallel = typeof maxParallel === 'undefined' ? -1 : maxParallel;
		onAfterChunk = typeof onAfterChunk === 'undefined' ? (_count) => {
		} : onAfterChunk;
		return (await Async.all1(this.toArray(), async (_1, _2) => {
					return (await mapFunc(_1, _2, this));
		}, maxParallel, onAfterChunk)).filterNull();
	}

	async mapNotNullAsync1(mapFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			let value = (await mapFunc(element, i++, this));
			if (value !== flat_null) {
				array.add0(value);
			}
		}
		return array;
	}

	flatMap1(mapFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			array.addAll(mapFunc(element, i++, this));
		}
		return array;
	}

	async flatMapAsync1(mapFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			array.addAll((await mapFunc(element, i++, this)));
		}
		return array;
	}

	async flatMapParallel1(mapFunc, maxParallel, onAfterChunk) {
		let self = this;

		maxParallel = typeof maxParallel === 'undefined' ? -1 : maxParallel;
		onAfterChunk = typeof onAfterChunk === 'undefined' ? (_count) => {
		} : onAfterChunk;
		return (await Async.all1(this, async (_1, _2) => {
					return (await mapFunc(_1, _2, this));
		}, maxParallel, onAfterChunk)).flatMap1((_x, _i, _array) => {
				return _x;
		});
	}

	forEach1(func) {
		{
			let i = 0;
			for (; i < this.accessor_count(); i++) {
				func(this.data[i], i, this);
			}
		}
		return this;
	}

	async forEachParallel1(func, maxParallel, onAfterChunk) {
		let self = this;

		maxParallel = typeof maxParallel === 'undefined' ? -1 : maxParallel;
		onAfterChunk = typeof onAfterChunk === 'undefined' ? (_count) => {
		} : onAfterChunk;
		(await Async.all1(this, async (_1, _2) => {
					(await func(_1, _2, this));
					return flat_null;
		}, maxParallel, onAfterChunk));
		return this;
	}

	async forEachAsync1(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			(await func(element, i++, this));
		}
		return this;
	}

	filterNull() {
		let self = this;

		return this.filter1((_x, _i, _array) => {
				return _x !== flat_null;
		});
	}

	filter1(filterFunc) {
		let flat_local_0 = flat_null;
		let filtered = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (filterFunc(element, i++, this)) {
				filtered.add0(element);
			}
		}
		return filtered;
	}

	async filterParallel1(filterFunc, maxParallel, onAfterChunk) {
		let self = this;

		let flat_local_0 = flat_null;
		maxParallel = typeof maxParallel === 'undefined' ? -1 : maxParallel;
		onAfterChunk = typeof onAfterChunk === 'undefined' ? (_count) => {
		} : onAfterChunk;
		let array = FlatArray.construct();
		let promiseArray = FlatArray.construct0(this.accessor_count(), undefined);
		let filtered = (await Async.all1(this, async (_1, _2) => {
					return Bool.construct((await filterFunc(_1, _2, this)));
		}, maxParallel, onAfterChunk));
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			let flat_local_1 = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (((flat_local_1 = (filtered.get(i++))) !== flat_null ? (flat_local_1.value) : false) === true) {
				array.add0(element);
			}
		}
		return array;
	}

	async filterAsync1(filterFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if ((await filterFunc(element, i++, this))) {
				array.add0(element);
			}
		}
		return array;
	}

	filterAtIndex(index, count) {
		let self = this;

		count = typeof count === 'undefined' ? 1 : count;
		return this.filter1((_x, _i, _array) => {
				return _i >= index && _i < index + count;
		});
	}

	uniqueBy1(prop) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let values = FlatArray.construct();
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			let x = prop(value);
			if (!values.contains0(x)) {
				array.add0(value);
				values.add0(x);
			}
		}
		return array;
	}

	unique() {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			if (!array.contains0(value)) {
				array.add0(value);
			}
		}
		return array;
	}

	take(howMany) {
		howMany = howMany > this.accessor_count() ? this.accessor_count() : howMany;
		let list = FlatArray.construct();
		{
			let i = 0;
			for (; i < howMany; i++) {
				list.add0(this.data[i]);
			}
		}
		return list;
	}

	skip(howMany) {
		let list = FlatArray.construct();
		{
			let i = howMany;
			for (; i < this.accessor_count(); i++) {
				list.add0(this.data[i]);
			}
		}
		return list;
	}

	takeWhile1(filterFunc) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			if (!filterFunc(value, i++, this)) {
				return array;
			}
			array.add0(value);
		}
		return array;
	}

	takeLast(howMany) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let startIndex = FlatMath.max1(0, this.accessor_count() - howMany);
		if (startIndex === this.accessor_count()) {
			return array;
		}
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			if (i++ >= startIndex) {
				array.add0(value);
			}
		}
		return array;
	}

	skipLast(howMany) {
		return this.take(this.accessor_count() - howMany);
	}

	join(delimiter, maxOutputCount, ellipsePosition, ellipse) {
		let self = this;

		delimiter = typeof delimiter === 'undefined' ? FlatString.construct5("") : delimiter;
		maxOutputCount = typeof maxOutputCount === 'undefined' ? this.accessor_count() : maxOutputCount;
		ellipsePosition = typeof ellipsePosition === 'undefined' ? ~~(maxOutputCount / 2) : ellipsePosition;
		ellipse = typeof ellipse === 'undefined' ? FlatString.construct5("...") : ellipse;
		maxOutputCount = maxOutputCount > this.accessor_count() ? this.accessor_count() : maxOutputCount;
		maxOutputCount = maxOutputCount < 0 ? 0 : maxOutputCount;
		if (maxOutputCount >= this.accessor_count()) {
			return this.reduce((_acc, _x, _i, _list) => {
					return _acc.plus0((_i > 0 ? delimiter : FlatString.construct5("")).plus0(_x.toString()));
				}, FlatString.construct5(""));
		}
		if (maxOutputCount === 0 && this.accessor_count() > 0) {
			return ellipse;
		}
		let str = FlatString.construct5("");
		{
			let i = 0;
			for (; i < ellipsePosition; i++) {
				str = str.concat(this.data[i].toString().plus0(delimiter));
			}
		}
		str = str.concat(ellipse);
		let start = this.accessor_count() - (maxOutputCount - ellipsePosition);
		{
			let i = start;
			for (; i < this.accessor_count(); i++) {
				str = str.concat(delimiter.plus0(this.data[i].toString()));
			}
		}
		return str;
	}

	reverse() {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct0(this.accessor_count(), undefined);
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			array.data[this.count - ++i] = element;
		}
		return array;
	}

	mergeSort(comparator) {
		let sorted = this.clone();
		if (this.accessor_count() > 1) {
			let mid = ~~(this.accessor_count() / 2);
			let lefthalf = this.take(mid).mergeSort(comparator);
			let righthalf = this.skip(mid).mergeSort(comparator);
			let l = 0;
			let r = 0;
			let i = 0;
			while (l < lefthalf.accessor_count() && r < righthalf.accessor_count()) {
				if (comparator(lefthalf.get(l), righthalf.get(r)) < 1) {
					sorted.set(i++, lefthalf.get(l++));
				} else {
					sorted.set(i++, righthalf.get(r++));
				}
			}
			while (l < lefthalf.accessor_count()) {
				sorted.set(i++, lefthalf.get(l++));
			}
			while (r < righthalf.accessor_count()) {
				sorted.set(i++, righthalf.get(r++));
			}
		}
		return sorted;
	}

	quickSort(comparator) {
		return FlatArray.quickSortHelper(this.clone(), 0, this.accessor_count() - 1, comparator);
	}

	static quickSortHelper(list, first, last, comparator) {
		if (first < last) {
			let mid = FlatArray.partition(list, first, last, comparator);
			FlatArray.quickSortHelper(list, first, mid - 1, comparator);
			FlatArray.quickSortHelper(list, mid + 1, last, comparator);
		}
		return list;
	}

	static partition(list, first, last, comparator) {
		let pivot = list.get(first);
		let l = first + 1;
		let r = last;
		while (l <= r) {
			while (l <= r && comparator(list.get(l), pivot) <= 0) {
				l++;
			}
			while (l <= r && comparator(list.get(r), pivot) >= 0) {
				r--;
			}
			if (l <= r) {
				list.swap(l, r);
			}
		}
		list.swap(first, r);
		return r;
	}

	sort(comparator) {
		return this.mergeSort(comparator);
	}

	toString() {
		let self = this;

		return FlatString.construct5("[").plus0(this.map1((_x, _i, _array) => {
					return _x !== flat_null && _x.accessor__js_class().isOfType(FlatString.accessor__js_class()) ? FlatString.construct5("\"").plus0((_x).toString().plus0(FlatString.construct5("\""))) : _x;
			}).join(FlatString.construct5(", "), undefined, undefined, undefined).plus0(FlatString.construct5("]")));
	}

	copy() {
		return this.skip(0);
	}

	static jsStringArrayToFlatArray(value) {
		let self = this;

		let count = 0;
		count = value.length;
		return FlatArray.construct0(count, undefined).map1((_x, _i, _array) => {
				let chars = flat_null;
				chars = value[_i];
				return FlatString.construct4(chars);
		});
	}

	cloneData() {
		return this.data.slice(0);
	}

	static generated48(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated62(value0, value1, value2, value3, value4, value5, value6, value7, value8) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		return FlatArray.construct1(temp, 9);
	}

	static generated204(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44, value45, value46, value47, value48, value49, value50, value51, value52, value53, value54, value55, value56, value57, value58, value59, value60, value61, value62, value63, value64, value65, value66, value67, value68, value69, value70, value71) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		temp[41] = value41;
		temp[42] = value42;
		temp[43] = value43;
		temp[44] = value44;
		temp[45] = value45;
		temp[46] = value46;
		temp[47] = value47;
		temp[48] = value48;
		temp[49] = value49;
		temp[50] = value50;
		temp[51] = value51;
		temp[52] = value52;
		temp[53] = value53;
		temp[54] = value54;
		temp[55] = value55;
		temp[56] = value56;
		temp[57] = value57;
		temp[58] = value58;
		temp[59] = value59;
		temp[60] = value60;
		temp[61] = value61;
		temp[62] = value62;
		temp[63] = value63;
		temp[64] = value64;
		temp[65] = value65;
		temp[66] = value66;
		temp[67] = value67;
		temp[68] = value68;
		temp[69] = value69;
		temp[70] = value70;
		temp[71] = value71;
		return FlatArray.construct1(temp, 72);
	}

	get(index) {
		return this.data[index];
	}

	set(index, value) {
		this.data[index] = value;
		return value;
	}

	mutator_first(value) {
		if (this.accessor_count() > 0) {
			this.set(0, value);
		} else {
			this.add0(value);
		}
		return value;
	}

	mutator_last(value) {
		if (this.accessor_count() > 0) {
			this.set(this.accessor_count() - 1, value);
		} else {
			this.add0(value);
		}
		return value;
	}

	static accessor__js_class() {
		return typeof FlatArray.__lazy_accessor__js_class !== 'undefined' ? FlatArray.__lazy_accessor__js_class : (FlatArray.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/Array"), false, FlatObject.accessor__js_class(), FlatArray.generated48(List.accessor__js_class(), OrderedList.accessor__js_class(), PlusOperator.accessor__js_class(), PlusEqualsOperator.accessor__js_class()), FlatArray.generated62(Field.construct(FlatString.construct5("capacity")), Field.construct(FlatString.construct5("count")), Field.construct(FlatString.construct5("position")), Field.construct(FlatString.construct5("data")), Field.construct(FlatString.construct5("isEmpty")), Field.construct(FlatString.construct5("isNotEmpty")), Field.construct(FlatString.construct5("iterator")), Field.construct(FlatString.construct5("first")), Field.construct(FlatString.construct5("last"))), FlatArray.generated204(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("getLast")), Function.construct(FlatString.construct5("fillRemaining")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("plusEquals")), Function.construct(FlatString.construct5("addAll")), Function.construct(FlatString.construct5("addUnique")), Function.construct(FlatString.construct5("add")), Function.construct(FlatString.construct5("add")), Function.construct(FlatString.construct5("unshift")), Function.construct(FlatString.construct5("shift")), Function.construct(FlatString.construct5("removeLast")), Function.construct(FlatString.construct5("removeWhere")), Function.construct(FlatString.construct5("remove")), Function.construct(FlatString.construct5("removeAtIndex")), Function.construct(FlatString.construct5("removeAtIndex")), Function.construct(FlatString.construct5("removeFirstWhere")), Function.construct(FlatString.construct5("remove")), Function.construct(FlatString.construct5("replace")), Function.construct(FlatString.construct5("replaceAt")), Function.construct(FlatString.construct5("replaceFirst")), Function.construct(FlatString.construct5("replaceLast")), Function.construct(FlatString.construct5("shiftRight")), Function.construct(FlatString.construct5("shiftLeft")), Function.construct(FlatString.construct5("swap")), Function.construct(FlatString.construct5("increaseSize")), Function.construct(FlatString.construct5("increaseSize")), Function.construct(FlatString.construct5("clone")), Function.construct(FlatString.construct5("toArray")), Function.construct(FlatString.construct5("clear")), Function.construct(FlatString.construct5("lastWhere")), Function.construct(FlatString.construct5("lastNonNull")), Function.construct(FlatString.construct5("map")), Function.construct(FlatString.construct5("mapParallel")), Function.construct(FlatString.construct5("mapAsync")), Function.construct(FlatString.construct5("mapNotNull")), Function.construct(FlatString.construct5("mapNotNullParallel")), Function.construct(FlatString.construct5("mapNotNullAsync")), Function.construct(FlatString.construct5("flatMap")), Function.construct(FlatString.construct5("flatMapAsync")), Function.construct(FlatString.construct5("flatMapParallel")), Function.construct(FlatString.construct5("forEach")), Function.construct(FlatString.construct5("forEachParallel")), Function.construct(FlatString.construct5("forEachAsync")), Function.construct(FlatString.construct5("filterNull")), Function.construct(FlatString.construct5("filter")), Function.construct(FlatString.construct5("filterParallel")), Function.construct(FlatString.construct5("filterAsync")), Function.construct(FlatString.construct5("filterAtIndex")), Function.construct(FlatString.construct5("uniqueBy")), Function.construct(FlatString.construct5("unique")), Function.construct(FlatString.construct5("take")), Function.construct(FlatString.construct5("skip")), Function.construct(FlatString.construct5("takeWhile")), Function.construct(FlatString.construct5("takeLast")), Function.construct(FlatString.construct5("skipLast")), Function.construct(FlatString.construct5("join")), Function.construct(FlatString.construct5("reverse")), Function.construct(FlatString.construct5("mergeSort")), Function.construct(FlatString.construct5("quickSort")), Function.construct(FlatString.construct5("quickSortHelper")), Function.construct(FlatString.construct5("partition")), Function.construct(FlatString.construct5("sort")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("copy")), Function.construct(FlatString.construct5("jsStringArrayToFlatArray")), Function.construct(FlatString.construct5("cloneData"))), this);
			})());
	}

	accessor_count() {
		return this.count;
	}

	mutator_count(value) {
		this.count = value;
		return value;
	}

	accessor_isEmpty() {
		return this.accessor_count() === 0;
	}

	accessor_isNotEmpty() {
		return !this.accessor_isEmpty();
	}

	accessor_iterator() {
		return ArrayIterator.construct(this);
	}

	accessor_first() {
		return this.accessor_count() > 0 ? this.get(0) : flat_null;
	}

	accessor_last() {
		return this.getLast(undefined);
	}

	static __assignments() {
	}
}

class Iterator {
	static generated74(value0, value1, value2, value3, value4, value5) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		return FlatArray.construct1(temp, 6);
	}

	static accessor__js_class() {
		return typeof Iterator.__lazy_accessor__js_class !== 'undefined' ? Iterator.__lazy_accessor__js_class : (Iterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/Iterator"), true, FlatObject.accessor__js_class(), FlatArray.construct(), Iterator.generated74(Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("hasPrevious")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("current")), Field.construct(FlatString.construct5("previous")), Field.construct(FlatString.construct5("next"))), FlatArray.construct(), this);
			})());
	}

	accessor_hasNext() {
		return false;
	}

	accessor_hasPrevious() {
		return false;
	}

	accessor_stepNext() {
		return flat_null;
	}

	accessor_current() {
		return flat_null;
	}

	accessor_previous() {
		return flat_null;
	}

	accessor_next() {
		return flat_null;
	}
}

class ArrayIterator extends FlatObject {
	array = flat_null;
	position = 0;

	static construct(array) {
		let __value = new ArrayIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		ArrayIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = ArrayIterator.__init.call(__value, array);

		return __value;
	}

	destroy() {
	}

	static __init(array) {
		let self = this;

		this.array = array;
		this.reset();
		return self;
	}

	reset() {
		this.position = 0;
		return this;
	}

	static generated1(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated41(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		return FlatArray.construct1(temp, 11);
	}

	static generated143(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	accessor_stepNext() {
		if (this.accessor_hasNext()) {
			return this.array.get(this.position++);
		}
		throw NoSuchElementException.construct(undefined);
	}

	static accessor__js_class() {
		return typeof ArrayIterator.__lazy_accessor__js_class !== 'undefined' ? ArrayIterator.__lazy_accessor__js_class : (ArrayIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/ArrayIterator"), false, FlatObject.accessor__js_class(), ArrayIterator.generated1(Iterator.accessor__js_class()), ArrayIterator.generated41(Field.construct(FlatString.construct5("position")), Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("hasPrevious")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("current")), Field.construct(FlatString.construct5("previous")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("allNext")), Field.construct(FlatString.construct5("allPrevious")), Field.construct(FlatString.construct5("allNextInclusive")), Field.construct(FlatString.construct5("allPreviousInclusive"))), ArrayIterator.generated143(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("reset"))), this);
			})());
	}

	accessor_hasNext() {
		return this.array.accessor_count() > this.position;
	}

	accessor_hasPrevious() {
		return this.position > 1;
	}

	accessor_current() {
		return this.position > 0 ? this.array.get(this.position - 1) : flat_null;
	}

	accessor_previous() {
		return this.accessor_hasPrevious() ? this.array.get(this.position - 2) : flat_null;
	}

	accessor_next() {
		return this.accessor_hasNext() ? this.array.get(this.position) : flat_null;
	}

	accessor_allNext() {
		return this.array.skip(this.position);
	}

	accessor_allPrevious() {
		return this.array.take(this.position);
	}

	accessor_allNextInclusive() {
		return this.array.skip(this.position > 0 ? this.position - 1 : 0);
	}

	accessor_allPreviousInclusive() {
		return this.array.take(this.position < this.array.accessor_count() ? this.position + 1 : this.array.accessor_count());
	}

	static __assignments() {
	}
}

class Async extends FlatObject {
	static construct() {
		let __value = new Async();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Async.__assignments.apply(__value, [].slice.call(arguments));
		__value = Async.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static async all0(promises) {
		let data = flat_null;
		let array = FlatArray.construct0(promises.accessor_count(), undefined);
		const result = await Promise.all(promises.data);
		result.forEach((entry) => {
				data = entry;
				array.add0(data);
		});
		return array;
	}

	static __init() {
		let self = this;

		return self;
	}

	static async all1(values, toPromise, maxParallel, onAfterChunk) {
		let input = flat_null;
		let data = flat_null;
		let localIndex = 0;
		maxParallel = typeof maxParallel === 'undefined' ? -1 : maxParallel;
		onAfterChunk = typeof onAfterChunk === 'undefined' ? (_count) => {
		} : onAfterChunk;
		let array = FlatArray.construct0(values.accessor_count(), undefined);
		let step = maxParallel;
		if (maxParallel <= 0) {
			step = values.accessor_count();
		}
		{
			let i = 0;
			for (; i < values.accessor_count(); i += step) {
				let count = 0;
				let promises = values.data
				.slice(i, i + step)
				.map((obj, index) => {
						input = obj;
						localIndex = index;
						return toPromise(input, i + localIndex);
				});
				count = promises.length;
				const result = await Promise.all(promises);
				result.forEach((entry) => {
						data = entry;
						array.add0(data);
				});
				(await onAfterChunk(count));
			}
		}
		return array;
	}

	static generated11(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static accessor__js_class() {
		return typeof Async.__lazy_accessor__js_class !== 'undefined' ? Async.__lazy_accessor__js_class : (Async.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/async/Async"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), Async.generated11(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("all")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("all"))), this);
			})());
	}

	static __assignments() {
	}
}

class Primitive extends FlatObject {
	static construct() {
		let __value = new Primitive();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Primitive.__assignments.apply(__value, [].slice.call(arguments));
		__value = Primitive.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated112(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof Primitive.__lazy_accessor__js_class !== 'undefined' ? Primitive.__lazy_accessor__js_class : (Primitive.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/primitive/Primitive"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), Primitive.generated112(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class Comparable {
	static generated9(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof Comparable.__lazy_accessor__js_class !== 'undefined' ? Comparable.__lazy_accessor__js_class : (Comparable.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/Comparable"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), Comparable.generated9(Function.construct(FlatString.construct5("compareTo"))), this);
			})());
	}
}

class Bool extends Primitive {
	value = false;

	static construct(value) {
		let __value = new Bool();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Primitive.__assignments.apply(__value, [].slice.call(arguments));
		Bool.__assignments.apply(__value, [].slice.call(arguments));
		__value = Bool.__init.call(__value, value);

		return __value;
	}

	destroy() {
	}

	static __init(value) {
		let self = this;

		this.value = value;
		return self;
	}

	compareTo0(other) {
		let flat_local_0 = flat_null;
		return this.value === ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : false) ? 0 : -1;
	}

	equals(other) {
		let flat_local_0 = flat_null;
		let flat_local_1 = flat_null;
		return ((other) !== flat_null || !((flat_local_0 = this) !== flat_null && flat_local_0.value)) && other.accessor__js_class().isOfType(Bool.accessor__js_class()) && this.value === (((flat_local_1 = (other)) !== flat_null ? (flat_local_1.value) : false));
	}

	toString() {
		return Bool.toString(this.value);
	}

	static toString(value) {
		return value ? FlatString.construct5("true") : FlatString.construct5("false");
	}

	static toJson(value, format, tab) {
		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		return Bool.toString(value);
	}

	static fromString(value) {
		return value.equals(FlatString.construct5("true")) ? true : false;
	}

	static generated3(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated27(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated80(value0, value1, value2, value3, value4, value5, value6, value7) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		return FlatArray.construct1(temp, 8);
	}

	static accessor__js_class() {
		return typeof Bool.__lazy_accessor__js_class !== 'undefined' ? Bool.__lazy_accessor__js_class : (Bool.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/primitive/Bool"), false, Primitive.accessor__js_class(), Bool.generated3(Comparable.accessor__js_class()), Bool.generated27(Field.construct(FlatString.construct5("value"))), Bool.generated80(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("fromString"))), this);
			})());
	}

	static __assignments() {
	}
}

class MultiplyEqualsOperator {
	static generated98(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof MultiplyEqualsOperator.__lazy_accessor__js_class !== 'undefined' ? MultiplyEqualsOperator.__lazy_accessor__js_class : (MultiplyEqualsOperator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/operators/MultiplyEqualsOperator"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), MultiplyEqualsOperator.generated98(Function.construct(FlatString.construct5("multiplyEquals"))), this);
			})());
	}
}

class MultiplyOperator {
	static generated115(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof MultiplyOperator.__lazy_accessor__js_class !== 'undefined' ? MultiplyOperator.__lazy_accessor__js_class : (MultiplyOperator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/operators/MultiplyOperator"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), MultiplyOperator.generated115(Function.construct(FlatString.construct5("multiply"))), this);
			})());
	}
}

class MinusEqualsOperator {
	static generated128(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof MinusEqualsOperator.__lazy_accessor__js_class !== 'undefined' ? MinusEqualsOperator.__lazy_accessor__js_class : (MinusEqualsOperator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/operators/MinusEqualsOperator"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), MinusEqualsOperator.generated128(Function.construct(FlatString.construct5("minusEquals"))), this);
			})());
	}
}

class MinusOperator {
	static generated101(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof MinusOperator.__lazy_accessor__js_class !== 'undefined' ? MinusOperator.__lazy_accessor__js_class : (MinusOperator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/operators/MinusOperator"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), MinusOperator.generated101(Function.construct(FlatString.construct5("minus"))), this);
			})());
	}
}

class Number extends Primitive {
	static construct() {
		let __value = new Number();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Primitive.__assignments.apply(__value, [].slice.call(arguments));
		Number.__assignments.apply(__value, [].slice.call(arguments));
		__value = Number.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated106(value0, value1, value2, value3, value4, value5, value6) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		return FlatArray.construct1(temp, 7);
	}

	static generated135(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated148(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		return FlatArray.construct1(temp, 18);
	}

	static accessor__js_class() {
		return typeof Number.__lazy_accessor__js_class !== 'undefined' ? Number.__lazy_accessor__js_class : (Number.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/primitive/number/Number"), false, Primitive.accessor__js_class(), Number.generated106(PlusOperator.accessor__js_class(), PlusEqualsOperator.accessor__js_class(), MinusOperator.accessor__js_class(), MinusEqualsOperator.accessor__js_class(), MultiplyOperator.accessor__js_class(), MultiplyEqualsOperator.accessor__js_class(), Comparable.accessor__js_class()), Number.generated135(Field.construct(FlatString.construct5("realValue")), Field.construct(FlatString.construct5("integerValue"))), Number.generated148(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("plusInteger")), Function.construct(FlatString.construct5("plusReal")), Function.construct(FlatString.construct5("plusEqualsInteger")), Function.construct(FlatString.construct5("plusEqualsReal")), Function.construct(FlatString.construct5("minusInteger")), Function.construct(FlatString.construct5("minusReal")), Function.construct(FlatString.construct5("minusEqualsInteger")), Function.construct(FlatString.construct5("minusEqualsReal")), Function.construct(FlatString.construct5("multiplyInteger")), Function.construct(FlatString.construct5("multiplyReal")), Function.construct(FlatString.construct5("multiplyEqualsInteger")), Function.construct(FlatString.construct5("multiplyEqualsReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor_realValue() {
		return 0;
	}

	accessor_integerValue() {
		return 0;
	}

	static __assignments() {
	}
}

class Integer {
	static accessor__js_class() {
		return typeof Integer.__lazy_accessor__js_class !== 'undefined' ? Integer.__lazy_accessor__js_class : (Integer.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/primitive/number/Integer"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), FlatArray.construct(), this);
			})());
	}
}

class FlatByte extends Number {
	value = 0;

	static construct(value) {
		let __value = new FlatByte();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Primitive.__assignments.apply(__value, [].slice.call(arguments));
		Number.__assignments.apply(__value, [].slice.call(arguments));
		FlatByte.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatByte.__init.call(__value, value);

		return __value;
	}

	destroy() {
	}

	static __init(value) {
		let self = this;

		this.value = value;
		return self;
	}

	compareTo0(other) {
		let flat_local_0 = flat_null;
		return this.value - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0);
	}

	compareToReal(other) {
		return this.value - other;
	}

	compareToInteger(other) {
		return this.value - other;
	}

	static compareTo(value, other) {
		return value - other;
	}

	static compareToReal(value, other) {
		return value - other;
	}

	static compareToInteger(value, other) {
		return value - other;
	}

	multiply0(other) {
		let flat_local_0 = flat_null;
		return FlatByte.construct(this.value * ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	multiplyReal(other) {
		return this.value * other;
	}

	multiplyInteger(other) {
		return this.value * other;
	}

	plus0(other) {
		let flat_local_0 = flat_null;
		return FlatByte.construct(this.value + ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	plusReal(other) {
		return this.value + other;
	}

	plusInteger(other) {
		return this.value + other;
	}

	plusEquals0(other) {
		let flat_local_0 = flat_null;
		return FlatByte.construct(this.value += ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	plusEqualsReal(other) {
		return this.value += other;
	}

	plusEqualsInteger(other) {
		return this.value += other;
	}

	minus(other) {
		let flat_local_0 = flat_null;
		return FlatByte.construct(this.value - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	minusReal(other) {
		return this.value - other;
	}

	minusInteger(other) {
		return this.value - other;
	}

	minusEquals(other) {
		let flat_local_0 = flat_null;
		return FlatByte.construct(this.value -= ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	minusEqualsReal(other) {
		return this.value -= other;
	}

	minusEqualsInteger(other) {
		return this.value -= other;
	}

	multiplyEquals(other) {
		let flat_local_0 = flat_null;
		return FlatByte.construct(this.value *= ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	multiplyEqualsReal(other) {
		return this.value *= other;
	}

	multiplyEqualsInteger(other) {
		return this.value *= other;
	}

	equals(other) {
		let flat_local_0 = flat_null;
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(Number.accessor__js_class()) && ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0) === this.value;
	}

	equals0(other) {
		return this.value === other;
	}

	equalsReal(other) {
		return this.value === other;
	}

	equalsInteger(other) {
		return this.value === other;
	}

	static equals(value, other) {
		return value === other;
	}

	static equalsReal(value, other) {
		return value === other;
	}

	static equalsInteger(value, other) {
		return value === other;
	}

	toString() {
		return FlatByte.toString(this.value);
	}

	static numDigits(number) {
		return FlatLong.numDigits(number);
	}

	static toString(value) {
		return FlatLong.toString(value);
	}

	static toJson(value, format, tab) {
		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		return FlatByte.toString(value);
	}

	static hashCodeLong(value) {
		return FlatMath.abs0(value);
	}

	static plus(a, b) {
		return a + b;
	}

	toEnglish() {
		return FlatByte.toEnglish(this.value);
	}

	static toEnglish(value) {
		return FlatLong.toEnglish(value);
	}

	static generated8(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated31(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated151(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		return FlatArray.construct1(temp, 41);
	}

	static accessor__js_class() {
		return typeof FlatByte.__lazy_accessor__js_class !== 'undefined' ? FlatByte.__lazy_accessor__js_class : (FlatByte.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/primitive/number/Byte"), false, Number.accessor__js_class(), FlatByte.generated8(Integer.accessor__js_class()), FlatByte.generated31(Field.construct(FlatString.construct5("realValue")), Field.construct(FlatString.construct5("integerValue")), Field.construct(FlatString.construct5("value"))), FlatByte.generated151(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("multiply")), Function.construct(FlatString.construct5("multiplyReal")), Function.construct(FlatString.construct5("multiplyInteger")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("plusReal")), Function.construct(FlatString.construct5("plusInteger")), Function.construct(FlatString.construct5("plusEquals")), Function.construct(FlatString.construct5("plusEqualsReal")), Function.construct(FlatString.construct5("plusEqualsInteger")), Function.construct(FlatString.construct5("minus")), Function.construct(FlatString.construct5("minusReal")), Function.construct(FlatString.construct5("minusInteger")), Function.construct(FlatString.construct5("minusEquals")), Function.construct(FlatString.construct5("minusEqualsReal")), Function.construct(FlatString.construct5("minusEqualsInteger")), Function.construct(FlatString.construct5("multiplyEquals")), Function.construct(FlatString.construct5("multiplyEqualsReal")), Function.construct(FlatString.construct5("multiplyEqualsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("numDigits")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("hashCodeLong")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("toEnglish")), Function.construct(FlatString.construct5("toEnglish"))), this);
			})());
	}

	accessor_realValue() {
		return this.value;
	}

	accessor_integerValue() {
		return this.value;
	}

	static __assignments() {
	}
}

class Exception extends FlatObject {
	stackTrace = flat_null;
	message = flat_null;

	static construct(message) {
		let __value = new Exception();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		__value = Exception.__init.call(__value, message);

		return __value;
	}

	destroy() {
	}

	static __init(message) {
		let self = this;

		message = typeof message === 'undefined' ? FlatString.construct5("") : message;
		this.message = message;
		this.error = Error(message.chars.data);
		return self;
	}

	toString() {
		return this.accessor__js_class().accessor_name().plus0(((this.message) !== flat_null ? FlatString.construct5(": ").plus0((this.message).plus0(FlatString.construct5(""))) : FlatString.construct5("")));
	}

	onThrown(soft) {
	}

	static catchType(func, exceptionType, soft, exact) {
		soft = typeof soft === 'undefined' ? false : soft;
		exact = typeof exact === 'undefined' ? false : exact;
		if (soft) {
			try {
				func();
			} catch (e)  {
				if (!(e instanceof Exception)) {
					console.log(0.8564170914635115);
					console.error(e);
					process.exit(1);
				} else {
					if (e.accessor__js_class().location.equals(exceptionType.location)) {
						return e;
					}
				}
			 }
			finally {
			}
		} else {
			try {
				func();
			} catch (e)  {
				if (!(e instanceof Exception)) {
					console.log(0.5113187041764021);
					console.error(e);
					process.exit(1);
				} else {
					if (e.accessor__js_class().location.equals(exceptionType.location)) {
						return e;
					} else {
						throw e;
					}
				}
			 }
			finally {
			}
		}
		return flat_null;
	}

	static async catchTypeAsync(func, exceptionType, soft, exact) {
		soft = typeof soft === 'undefined' ? false : soft;
		exact = typeof exact === 'undefined' ? false : exact;
		if (soft) {
			try {
				(await func());
			} catch (e)  {
				if (!(e instanceof Exception)) {
					console.log(0.8656339837897973);
					console.error(e);
					process.exit(1);
				} else {
					if (e.accessor__js_class().location.equals(exceptionType.location)) {
						return e;
					}
				}
			 }
			finally {
			}
		} else {
			try {
				(await func());
			} catch (e)  {
				if (!(e instanceof Exception)) {
					console.log(0.3978167381084017);
					console.error(e);
					process.exit(1);
				} else {
					if (e.accessor__js_class().location.equals(exceptionType.location)) {
						return e;
					} else {
						throw e;
					}
				}
			 }
			finally {
			}
		}
		return flat_null;
	}

	static generated23(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated39(value0, value1, value2, value3, value4, value5) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		return FlatArray.construct1(temp, 6);
	}

	static accessor__js_class() {
		return typeof Exception.__lazy_accessor__js_class !== 'undefined' ? Exception.__lazy_accessor__js_class : (Exception.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/exception/Exception"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Exception.generated23(Field.construct(FlatString.construct5("stackTrace")), Field.construct(FlatString.construct5("message"))), Exception.generated39(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("onThrown")), Function.construct(FlatString.construct5("catchType")), Function.construct(FlatString.construct5("catchTypeAsync"))), this);
			})());
	}

	static __assignments() {
		this.stackTrace = StackTrace.construct();
	}
}

class CancellationException extends Exception {
	static construct(message) {
		let __value = new CancellationException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		CancellationException.__assignments.apply(__value, [].slice.call(arguments));
		__value = CancellationException.__init.call(__value, message);

		return __value;
	}

	destroy() {
	}

	static __init(message) {
		let self = this;

		message = typeof message === 'undefined' ? flat_null : message;
		self = Exception.__init.call(this, message);
		return self;
	}

	static generated7(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof CancellationException.__lazy_accessor__js_class !== 'undefined' ? CancellationException.__lazy_accessor__js_class : (CancellationException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/future/CancellationException"), false, Exception.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), CancellationException.generated7(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class CaughtException extends FlatObject {
	type = flat_null;
	soft = false;

	static construct(type, soft) {
		let __value = new CaughtException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		CaughtException.__assignments.apply(__value, [].slice.call(arguments));
		__value = CaughtException.__init.call(__value, type, soft);

		return __value;
	}

	destroy() {
	}

	static __init(type, soft) {
		let self = this;

		soft = typeof soft === 'undefined' ? true : soft;
		this.type = type;
		this.soft = soft;
		return self;
	}

	toString() {
		return FlatString.construct5("Catching ").plus0((this.type).toString().plus0(FlatString.construct5(", soft: ").plus0(Bool.toString((this.soft)).plus0(FlatString.construct5("")))));
	}

	static generated4(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated24(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static accessor__js_class() {
		return typeof CaughtException.__lazy_accessor__js_class !== 'undefined' ? CaughtException.__lazy_accessor__js_class : (CaughtException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/exception/CaughtException"), false, FlatObject.accessor__js_class(), FlatArray.construct(), CaughtException.generated4(Field.construct(FlatString.construct5("type")), Field.construct(FlatString.construct5("soft"))), CaughtException.generated24(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("toString"))), this);
			})());
	}

	static __assignments() {
	}
}

class Char extends Number {
	value = 0;

	static construct(value) {
		let __value = new Char();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Primitive.__assignments.apply(__value, [].slice.call(arguments));
		Number.__assignments.apply(__value, [].slice.call(arguments));
		Char.__assignments.apply(__value, [].slice.call(arguments));
		__value = Char.__init.call(__value, value);

		return __value;
	}

	destroy() {
	}

	static __init(value) {
		let self = this;

		this.value = value;
		return self;
	}

	static toLowerCase(c) {
		let id = c.charCodeAt(0);
		if (id >= 65 && id <= 90) {
			return String.fromCharCode((id + 32));
		}
		return c;
	}

	static toUpperCase(c) {
		let id = c.charCodeAt(0);
		if (id >= 97 && id <= 122) {
			return String.fromCharCode((id - 32));
		}
		return c;
	}

	repeat(times) {
		return Char.repeat(this.value, times);
	}

	static repeat(value, times) {
		return Char.toString((value)).plus0(FlatString.construct5("")).repeat(times);
	}

	toLowerCase() {
		return Char.toLowerCase(this.value);
	}

	toUpperCase() {
		return Char.toUpperCase(this.value);
	}

	compareTo0(other) {
		let flat_local_0 = flat_null;
		return this.value.charCodeAt(0) - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0).charCodeAt(0);
	}

	compareToReal(other) {
		return this.value.charCodeAt(0) - other;
	}

	compareToInteger(other) {
		return this.value.charCodeAt(0) - other;
	}

	static compareTo(value, other) {
		return value.charCodeAt(0) - other.charCodeAt(0);
	}

	static compareToReal(value, other) {
		return value.charCodeAt(0) - other;
	}

	static compareToInteger(value, other) {
		return value.charCodeAt(0) - other;
	}

	multiply0(other) {
		let flat_local_0 = flat_null;
		return Char.construct(this.value.charCodeAt(0) * ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0).charCodeAt(0));
	}

	multiplyReal(other) {
		return this.value.charCodeAt(0) * other;
	}

	multiplyInteger(other) {
		return this.value.charCodeAt(0) * other;
	}

	plus0(other) {
		let flat_local_0 = flat_null;
		return Char.construct(this.value.charCodeAt(0) + ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0).charCodeAt(0));
	}

	plusReal(other) {
		return this.value.charCodeAt(0) + other;
	}

	plusInteger(other) {
		return this.value.charCodeAt(0) + other;
	}

	plusEquals0(other) {
		let flat_local_0 = flat_null;
		return Char.construct((this.value = String.fromCharCode(this.value.charCodeAt(0) + ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0).charCodeAt(0))));
	}

	plusEqualsReal(other) {
		return (this.value = String.fromCharCode(this.value.charCodeAt(0) + other));
	}

	plusEqualsInteger(other) {
		return (this.value = String.fromCharCode(this.value.charCodeAt(0) + other));
	}

	minus(other) {
		let flat_local_0 = flat_null;
		return Char.construct(this.value.charCodeAt(0) - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0).charCodeAt(0));
	}

	minusReal(other) {
		return this.value.charCodeAt(0) - other;
	}

	minusInteger(other) {
		return this.value.charCodeAt(0) - other;
	}

	minusEquals(other) {
		let flat_local_0 = flat_null;
		return Char.construct(this.value.charCodeAt(0) - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0).charCodeAt(0));
	}

	minusEqualsReal(other) {
		return (this.value = String.fromCharCode(this.value.charCodeAt(0) - other));
	}

	minusEqualsInteger(other) {
		return (this.value = String.fromCharCode(this.value.charCodeAt(0) - other));
	}

	multiplyEquals(other) {
		let flat_local_0 = flat_null;
		return Char.construct((this.value = String.fromCharCode(this.value.charCodeAt(0) * ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0).charCodeAt(0))));
	}

	multiplyEqualsReal(other) {
		return (this.value = String.fromCharCode(this.value.charCodeAt(0) * other));
	}

	multiplyEqualsInteger(other) {
		return (this.value = String.fromCharCode(this.value.charCodeAt(0) * other));
	}

	equals(other) {
		let flat_local_0 = flat_null;
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(Number.accessor__js_class()) && ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0) === this.value;
	}

	equals0(other) {
		return this.value === other;
	}

	equalsReal(other) {
		return this.value === other;
	}

	equalsInteger(other) {
		return this.value === other;
	}

	static equals(value, other) {
		return value === other;
	}

	static equalsReal(value, other) {
		return value === other;
	}

	static equalsInteger(value, other) {
		return value === other;
	}

	static isNumber(value) {
		let char = value.charCodeAt(0);
		return char >= '0'.charCodeAt(0) && char <= '9'.charCodeAt(0);
	}

	static isWhitespace(value) {
		switch (value) {
			case ' ':
			case '\t':
			case '\r':
			case '\n':
			return true;
		}
		return false;
	}

	toString() {
		return Char.toString(this.value);
	}

	static toString(c) {
		return FlatString.construct2(c);
	}

	static toJson(c, format, tab) {
		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		return Char.toString(c);
	}

	static hashCodeLong(value) {
		return FlatMath.abs0(value);
	}

	static plus(a, b) {
		return a.charCodeAt(0) + b;
	}

	static generated6(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated32(value0, value1, value2, value3, value4) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		return FlatArray.construct1(temp, 5);
	}

	static generated157(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44, value45) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		temp[41] = value41;
		temp[42] = value42;
		temp[43] = value43;
		temp[44] = value44;
		temp[45] = value45;
		return FlatArray.construct1(temp, 46);
	}

	static accessor__js_class() {
		return typeof Char.__lazy_accessor__js_class !== 'undefined' ? Char.__lazy_accessor__js_class : (Char.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/primitive/number/Char"), false, Number.accessor__js_class(), Char.generated6(Integer.accessor__js_class()), Char.generated32(Field.construct(FlatString.construct5("realValue")), Field.construct(FlatString.construct5("integerValue")), Field.construct(FlatString.construct5("isNumber")), Field.construct(FlatString.construct5("isWhitespace")), Field.construct(FlatString.construct5("value"))), Char.generated157(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("toLowerCase")), Function.construct(FlatString.construct5("toUpperCase")), Function.construct(FlatString.construct5("repeat")), Function.construct(FlatString.construct5("repeat")), Function.construct(FlatString.construct5("toLowerCase")), Function.construct(FlatString.construct5("toUpperCase")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("multiply")), Function.construct(FlatString.construct5("multiplyReal")), Function.construct(FlatString.construct5("multiplyInteger")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("plusReal")), Function.construct(FlatString.construct5("plusInteger")), Function.construct(FlatString.construct5("plusEquals")), Function.construct(FlatString.construct5("plusEqualsReal")), Function.construct(FlatString.construct5("plusEqualsInteger")), Function.construct(FlatString.construct5("minus")), Function.construct(FlatString.construct5("minusReal")), Function.construct(FlatString.construct5("minusInteger")), Function.construct(FlatString.construct5("minusEquals")), Function.construct(FlatString.construct5("minusEqualsReal")), Function.construct(FlatString.construct5("minusEqualsInteger")), Function.construct(FlatString.construct5("multiplyEquals")), Function.construct(FlatString.construct5("multiplyEqualsReal")), Function.construct(FlatString.construct5("multiplyEqualsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("isNumber")), Function.construct(FlatString.construct5("isWhitespace")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("hashCodeLong")), Function.construct(FlatString.construct5("plus"))), this);
			})());
	}

	accessor_realValue() {
		return this.value;
	}

	accessor_integerValue() {
		return this.value;
	}

	accessor_isNumber() {
		return Char.isNumber(this.value);
	}

	accessor_isWhitespace() {
		return Char.isWhitespace(this.value);
	}

	static __assignments() {
	}
}

class Class extends FlatObject {
	isInterface = false;
	location = flat_null;
	extension = flat_null;
	interfaces = flat_null;
	fields = flat_null;
	functions = flat_null;
	jsClass = flat_null;

	static construct0(location, isInterface, extension, interfaces, fields, functions) {
		let __value = new Class();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Class.__assignments.apply(__value, [].slice.call(arguments));
		__value = Class.__init0.call(__value, location, isInterface, extension, interfaces, fields, functions);

		return __value;
	}

	static construct1(location, isInterface, extension, interfaces, fields, functions, jsClass) {
		let __value = new Class();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Class.__assignments.apply(__value, [].slice.call(arguments));
		__value = Class.__init1.call(__value, location, isInterface, extension, interfaces, fields, functions, jsClass);

		return __value;
	}

	destroy() {
	}

	static __init0(location, isInterface, extension, interfaces, fields, functions) {
		let self = this;

		fields = typeof fields === 'undefined' ? flat_null : fields;
		functions = typeof functions === 'undefined' ? flat_null : functions;
		this.location = location;
		this.isInterface = isInterface;
		this.extension = extension;
		this.interfaces = interfaces;
		this.fields = fields;
		this.functions = functions;
		return self;
	}

	getField(name, checkAncestors) {
		let self = this;

		let flat_local_0 = flat_null;
		let flat_local_1 = flat_null;
		let flat_local_2 = flat_null;
		let flat_local_3 = flat_null;
		checkAncestors = typeof checkAncestors === 'undefined' ? false : checkAncestors;
		return (flat_local_0 = this.fields.firstWhere0((_x, _i, _list) => {
					return _x.name.equals(name);
		})) !== flat_null ? flat_local_0 : (flat_local_1 = (checkAncestors ? ((flat_local_2 = this.extension) !== flat_null ? (flat_local_2.getField(name, true)) : flat_null) : flat_null)) !== flat_null ? flat_local_1 : (checkAncestors ? ((flat_local_3 = this.interfaces.mapNotNull1((_x, _i, _array) => {
							return _x.getField(name, true);
				})) !== flat_null ? (flat_local_3.accessor_first()) : flat_null) : flat_null);
	}

	isOfType(other) {
		let self = this;

		return (other.isInterface ? this.isOfTypeInterface(other) || (this.extension) !== flat_null && this.extension.isOfType(other) : this.isOfTypeClass(other) || this.interfaces.any0((_x, _i, _list) => {
					return _x.isOfTypeClass(other);
		})) || FlatObject.referenceEquals(other, FlatObject.accessor__js_class());
	}

	isOfTypeClass(other) {
		let current = this;
		while ((current) !== flat_null) {
			if (FlatObject.referenceEquals(current, other)) {
				return true;
			}
			current = current.extension;
		}
		return false;
	}

	isOfTypeInterface(other) {
		let self = this;

		let current = this;
		while ((current) !== flat_null) {
			if (current.interfaces.any0((_x, _i, _list) => {
						return _x.equals(other) || _x.isOfTypeInterface(other);
			})) {
				return true;
			}
			current = current.extension;
		}
		return false;
	}

	getClassesOfType(baseType) {
		let self = this;

		return Class.accessor_ALL().filter1((_x, _i, _array) => {
				return _x.isOfType(baseType);
		});
	}

	equals(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(Class.accessor__js_class()) && this.equals0(other);
	}

	equals0(another) {
		return this.location.chars.equals(another.location.chars);
	}

	toString() {
		return FlatString.construct5("Class \"").plus0((this.location).plus0(FlatString.construct5("\"")));
	}

	static __init1(location, isInterface, extension, interfaces, fields, functions, jsClass) {
		let self = this;

		fields = typeof fields === 'undefined' ? flat_null : fields;
		functions = typeof functions === 'undefined' ? flat_null : functions;
		jsClass = typeof jsClass === 'undefined' ? flat_null : jsClass;
		this.location = location;
		this.isInterface = isInterface;
		this.extension = extension;
		this.interfaces = interfaces;
		this.fields = fields;
		this.functions = functions;
		this.jsClass = jsClass;
		return self;
	}

	static generated22(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		return FlatArray.construct1(temp, 11);
	}

	static generated145(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		return FlatArray.construct1(temp, 12);
	}

	static generated246(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44, value45, value46, value47, value48, value49, value50, value51, value52, value53, value54, value55, value56, value57, value58, value59, value60, value61, value62, value63, value64, value65, value66, value67, value68, value69, value70, value71, value72, value73, value74, value75, value76, value77, value78, value79, value80, value81, value82, value83, value84, value85, value86, value87, value88, value89, value90, value91, value92, value93, value94, value95, value96, value97, value98, value99, value100, value101, value102, value103, value104, value105, value106, value107, value108, value109, value110, value111, value112, value113, value114, value115, value116, value117, value118, value119, value120, value121, value122, value123) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		temp[41] = value41;
		temp[42] = value42;
		temp[43] = value43;
		temp[44] = value44;
		temp[45] = value45;
		temp[46] = value46;
		temp[47] = value47;
		temp[48] = value48;
		temp[49] = value49;
		temp[50] = value50;
		temp[51] = value51;
		temp[52] = value52;
		temp[53] = value53;
		temp[54] = value54;
		temp[55] = value55;
		temp[56] = value56;
		temp[57] = value57;
		temp[58] = value58;
		temp[59] = value59;
		temp[60] = value60;
		temp[61] = value61;
		temp[62] = value62;
		temp[63] = value63;
		temp[64] = value64;
		temp[65] = value65;
		temp[66] = value66;
		temp[67] = value67;
		temp[68] = value68;
		temp[69] = value69;
		temp[70] = value70;
		temp[71] = value71;
		temp[72] = value72;
		temp[73] = value73;
		temp[74] = value74;
		temp[75] = value75;
		temp[76] = value76;
		temp[77] = value77;
		temp[78] = value78;
		temp[79] = value79;
		temp[80] = value80;
		temp[81] = value81;
		temp[82] = value82;
		temp[83] = value83;
		temp[84] = value84;
		temp[85] = value85;
		temp[86] = value86;
		temp[87] = value87;
		temp[88] = value88;
		temp[89] = value89;
		temp[90] = value90;
		temp[91] = value91;
		temp[92] = value92;
		temp[93] = value93;
		temp[94] = value94;
		temp[95] = value95;
		temp[96] = value96;
		temp[97] = value97;
		temp[98] = value98;
		temp[99] = value99;
		temp[100] = value100;
		temp[101] = value101;
		temp[102] = value102;
		temp[103] = value103;
		temp[104] = value104;
		temp[105] = value105;
		temp[106] = value106;
		temp[107] = value107;
		temp[108] = value108;
		temp[109] = value109;
		temp[110] = value110;
		temp[111] = value111;
		temp[112] = value112;
		temp[113] = value113;
		temp[114] = value114;
		temp[115] = value115;
		temp[116] = value116;
		temp[117] = value117;
		temp[118] = value118;
		temp[119] = value119;
		temp[120] = value120;
		temp[121] = value121;
		temp[122] = value122;
		temp[123] = value123;
		return FlatArray.construct1(temp, 124);
	}

	static accessor__js_class() {
		return typeof Class.__lazy_accessor__js_class !== 'undefined' ? Class.__lazy_accessor__js_class : (Class.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/meta/Class"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Class.generated22(Field.construct(FlatString.construct5("isInterface")), Field.construct(FlatString.construct5("location")), Field.construct(FlatString.construct5("package")), Field.construct(FlatString.construct5("name")), Field.construct(FlatString.construct5("rootName")), Field.construct(FlatString.construct5("extension")), Field.construct(FlatString.construct5("interfaces")), Field.construct(FlatString.construct5("fields")), Field.construct(FlatString.construct5("functions")), Field.construct(FlatString.construct5("jsClass")), Field.construct(FlatString.construct5("ALL"))), Class.generated145(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("getField")), Function.construct(FlatString.construct5("isOfType")), Function.construct(FlatString.construct5("isOfTypeClass")), Function.construct(FlatString.construct5("isOfTypeInterface")), Function.construct(FlatString.construct5("getClassesOfType")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor__js_package() {
		return this.location.substring(0, this.location.lastIndexOf2(FlatString.construct5("/"), undefined, 0));
	}

	accessor_name() {
		return this.location.substring(this.location.lastIndexOf2(FlatString.construct5("/"), undefined, undefined) + 1, undefined);
	}

	accessor_rootName() {
		return this.location.substring(FlatMath.max1(this.location.lastIndexOf2(FlatString.construct5("/"), undefined, undefined), this.location.lastIndexOf2(FlatString.construct5("."), undefined, undefined)) + 1, undefined);
	}

	static accessor_ALL() {
		return typeof Class.__lazy_accessor_ALL !== 'undefined' ? Class.__lazy_accessor_ALL : (Class.__lazy_accessor_ALL = (() => {
					return Class.generated246(App.accessor__js_class(), FlatArray.accessor__js_class(), ArrayIterator.accessor__js_class(), Async.accessor__js_class(), Bool.accessor__js_class(), FlatByte.accessor__js_class(), CancellationException.accessor__js_class(), CaughtException.accessor__js_class(), Char.accessor__js_class(), Class.accessor__js_class(), ClosedStreamException.accessor__js_class(), Colorizer.accessor__js_class(), Comparable.accessor__js_class(), FlatConsole.accessor__js_class(), ConsoleOutputStream.accessor__js_class(), CumulativeTimer.accessor__js_class(), Curl.accessor__js_class(), FlatDate.accessor__js_class(), FlatDateTime.accessor__js_class(), DivideByZeroException.accessor__js_class(), FlatDouble.accessor__js_class(), EmptyOutputStream.accessor__js_class(), EmptyStackException.accessor__js_class(), EscapeCode.accessor__js_class(), EscapeCodeIterator.accessor__js_class(), EventStream.accessor__js_class(), Exception.accessor__js_class(), ExceptionData.accessor__js_class(), ExecutionResponse.accessor__js_class(), FancyOutputStream.accessor__js_class(), Field.accessor__js_class(), File.accessor__js_class(), FileNotFoundException.accessor__js_class(), FileReader.accessor__js_class(), FileWriter.accessor__js_class(), FilterIterator.accessor__js_class(), FilterNotIterator.accessor__js_class(), FlatMapIterator.accessor__js_class(), FlatFloat.accessor__js_class(), Function.accessor__js_class(), Future.accessor__js_class(), GreaterThanOperator.accessor__js_class(), GreaterThanOrEqualToOperator.accessor__js_class(), HashMap.accessor__js_class(), HashSet.accessor__js_class(), Import.accessor__js_class(), Import_Test.accessor__js_class(), Import_TestSuite.accessor__js_class(), InputStream.accessor__js_class(), FlatInt.accessor__js_class(), IntRange.accessor__js_class(), IntRangeIterator.accessor__js_class(), Integer.accessor__js_class(), InvalidArgumentException.accessor__js_class(), InvalidAssertionException.accessor__js_class(), InvalidOperationException.accessor__js_class(), Iterable.accessor__js_class(), Iterator.accessor__js_class(), LessThanOperator.accessor__js_class(), LessThanOrEqualToOperator.accessor__js_class(), LinkedList.accessor__js_class(), LinkedListIterator.accessor__js_class(), List.accessor__js_class(), ListNode.accessor__js_class(), Logger.accessor__js_class(), FlatLong.accessor__js_class(), MapIterator.accessor__js_class(), Match.accessor__js_class(), MatchGroup.accessor__js_class(), FlatMath.accessor__js_class(), MinusEqualsOperator.accessor__js_class(), MinusOperator.accessor__js_class(), MultiplyEqualsOperator.accessor__js_class(), MultiplyOperator.accessor__js_class(), NoSuchElementException.accessor__js_class(), NotEqualToOperator.accessor__js_class(), Null.accessor__js_class(), NullAccessException.accessor__js_class(), Number.accessor__js_class(), FlatObject.accessor__js_class(), OrderedList.accessor__js_class(), OutputStream.accessor__js_class(), Pair.accessor__js_class(), Pattern.accessor__js_class(), PeekIterator.accessor__js_class(), PlusEqualsOperator.accessor__js_class(), PlusOperator.accessor__js_class(), Primitive.accessor__js_class(), Queue.accessor__js_class(), RealNumber.accessor__js_class(), Regex.accessor__js_class(), RegexStringExtensions.accessor__js_class(), FlatShort.accessor__js_class(), SkipIterator.accessor__js_class(), Stack.accessor__js_class(), StackTrace.accessor__js_class(), Stream.accessor__js_class(), StreamIterator.accessor__js_class(), StreamListExtensions.accessor__js_class(), StreamReader.accessor__js_class(), FlatString.accessor__js_class(), StringBuilder.accessor__js_class(), StringCharArray.accessor__js_class(), SyntaxStringFunctions.accessor__js_class(), System.accessor__js_class(), TakeIterator.accessor__js_class(), Test.accessor__js_class(), TestAsync.accessor__js_class(), TestCase.accessor__js_class(), TestException.accessor__js_class(), TestResult.accessor__js_class(), TestRunner.accessor__js_class(), TestRunnerModel.accessor__js_class(), TestSuite.accessor__js_class(), TestSuiteRunner.accessor__js_class(), TestSuiteRunnerModel.accessor__js_class(), Thread.accessor__js_class(), ThreadLocal.accessor__js_class(), Time.accessor__js_class(), Timer.accessor__js_class(), UncaughtExceptionHandler.accessor__js_class(), UnimplementedOperationException.accessor__js_class(), UniqueIterator.accessor__js_class(), WildcardHelper.accessor__js_class());
			})());
	}

	static __assignments() {
	}
}

class ClosedStreamException extends Exception {
	static construct(message) {
		let __value = new ClosedStreamException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		ClosedStreamException.__assignments.apply(__value, [].slice.call(arguments));
		__value = ClosedStreamException.__init.call(__value, message);

		return __value;
	}

	destroy() {
	}

	static __init(message) {
		let self = this;

		message = typeof message === 'undefined' ? FlatString.construct5("Trying to use closed stream") : message;
		self = Exception.__init.call(this, message);
		return self;
	}

	static generated12(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof ClosedStreamException.__lazy_accessor__js_class !== 'undefined' ? ClosedStreamException.__lazy_accessor__js_class : (ClosedStreamException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/ClosedStreamException"), false, Exception.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), ClosedStreamException.generated12(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class Colorizer extends FlatObject {
	static PREFIX = flat_null;
	static RESET = flat_null;
	static BRIGHT = flat_null;
	static DIM = flat_null;
	static UNDERSCORE = flat_null;
	static BLINK = flat_null;
	static REVERSE = flat_null;
	static HIDDEN = flat_null;
	static BLACK_FOREGROUND = flat_null;
	static RED_FOREGROUND = flat_null;
	static GREEN_FOREGROUND = flat_null;
	static YELLOW_FOREGROUND = flat_null;
	static BLUE_FOREGROUND = flat_null;
	static MAGENTA_FOREGROUND = flat_null;
	static CYAN_FOREGROUND = flat_null;
	static WHITE_FOREGROUND = flat_null;
	static RGB_FOREGROUND = flat_null;
	static GRAY_FOREGROUND = flat_null;
	static BRIGHT_BLACK_FOREGROUND = flat_null;
	static BRIGHT_RED_FOREGROUND = flat_null;
	static BRIGHT_GREEN_FOREGROUND = flat_null;
	static BRIGHT_YELLOW_FOREGROUND = flat_null;
	static BRIGHT_BLUE_FOREGROUND = flat_null;
	static BRIGHT_MAGENTA_FOREGROUND = flat_null;
	static BRIGHT_CYAN_FOREGROUND = flat_null;
	static BRIGHT_WHITE_FOREGROUND = flat_null;
	static BLACK_BACKGROUND = flat_null;
	static RED_BACKGROUND = flat_null;
	static GREEN_BACKGROUND = flat_null;
	static YELLOW_BACKGROUND = flat_null;
	static BLUE_BACKGROUND = flat_null;
	static MAGENTA_BACKGROUND = flat_null;
	static CYAN_BACKGROUND = flat_null;
	static WHITE_BACKGROUND = flat_null;
	static RGB_BACKGROUND = flat_null;
	static GRAY_BACKGROUND = flat_null;
	static BRIGHT_BLACK_BACKGROUND = flat_null;
	static BRIGHT_RED_BACKGROUND = flat_null;
	static BRIGHT_GREEN_BACKGROUND = flat_null;
	static BRIGHT_YELLOW_BACKGROUND = flat_null;
	static BRIGHT_BLUE_BACKGROUND = flat_null;
	static BRIGHT_MAGENTA_BACKGROUND = flat_null;
	static BRIGHT_CYAN_BACKGROUND = flat_null;
	static BRIGHT_WHITE_BACKGROUND = flat_null;

	static construct() {
		let __value = new Colorizer();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Colorizer.__assignments.apply(__value, [].slice.call(arguments));
		__value = Colorizer.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static format(code, value) {
		let stripped = flat_null;
		if (!code.equals(Colorizer.RESET)) {
			stripped = value.replace((Colorizer.PREFIX).plus0(FlatString.construct5("").plus0((Colorizer.RESET).plus0(FlatString.construct5("m")))), (Colorizer.PREFIX).plus0(FlatString.construct5("").plus0((Colorizer.RESET).plus0(FlatString.construct5("m").plus0((Colorizer.PREFIX).plus0(FlatString.construct5("").plus0((code).plus0(FlatString.construct5("m")))))))), undefined);
		} else {
			stripped = value;
		}
		return (Colorizer.PREFIX).plus0(FlatString.construct5("").plus0((code).plus0(FlatString.construct5("m").plus0((stripped).plus0(FlatString.construct5("").plus0((Colorizer.PREFIX).plus0(FlatString.construct5("").plus0((Colorizer.RESET).plus0(FlatString.construct5("m"))))))))));
	}

	static reset(value) {
		return Colorizer.format(Colorizer.RESET, value);
	}

	static bright(value) {
		return Colorizer.format(Colorizer.BRIGHT, value);
	}

	static dim(value) {
		return Colorizer.format(Colorizer.DIM, value);
	}

	static underscore(value) {
		return Colorizer.format(Colorizer.UNDERSCORE, value);
	}

	static blink(value) {
		return Colorizer.format(Colorizer.BLINK, value);
	}

	static reverse(value) {
		return Colorizer.format(Colorizer.REVERSE, value);
	}

	static hidden(value) {
		return Colorizer.format(Colorizer.HIDDEN, value);
	}

	static rgb(value, r, g, b) {
		return Colorizer.format(Colorizer.RGB_FOREGROUND.plus0(FlatString.construct5(";2;").plus0(FlatInt.toString((r)).plus0(FlatString.construct5(";").plus0(FlatInt.toString((g)).plus0(FlatString.construct5(";").plus0(FlatInt.toString((b)).plus0(FlatString.construct5("")))))))), value);
	}

	static foreground256(value, num) {
		return Colorizer.format(Colorizer.RGB_FOREGROUND.plus0(FlatString.construct5(";5;").plus0(FlatInt.toString((num)).plus0(FlatString.construct5("")))), value);
	}

	static black(value) {
		return Colorizer.format(Colorizer.BLACK_FOREGROUND, value);
	}

	static red(value) {
		return Colorizer.format(Colorizer.RED_FOREGROUND, value);
	}

	static green(value) {
		return Colorizer.format(Colorizer.GREEN_FOREGROUND, value);
	}

	static yellow(value) {
		return Colorizer.format(Colorizer.YELLOW_FOREGROUND, value);
	}

	static blue(value) {
		return Colorizer.format(Colorizer.BLUE_FOREGROUND, value);
	}

	static magenta(value) {
		return Colorizer.format(Colorizer.MAGENTA_FOREGROUND, value);
	}

	static cyan(value) {
		return Colorizer.format(Colorizer.CYAN_FOREGROUND, value);
	}

	static white(value) {
		return Colorizer.format(Colorizer.WHITE_FOREGROUND, value);
	}

	static gray(value) {
		return Colorizer.format(Colorizer.GRAY_FOREGROUND, value);
	}

	static brightBlack(value) {
		return Colorizer.format(Colorizer.GRAY_FOREGROUND, value);
	}

	static brightRed(value) {
		return Colorizer.format(Colorizer.RED_FOREGROUND, value);
	}

	static brightGreen(value) {
		return Colorizer.format(Colorizer.GREEN_FOREGROUND, value);
	}

	static brightYellow(value) {
		return Colorizer.format(Colorizer.YELLOW_FOREGROUND, value);
	}

	static brightBlue(value) {
		return Colorizer.format(Colorizer.BLUE_FOREGROUND, value);
	}

	static brightMagenta(value) {
		return Colorizer.format(Colorizer.MAGENTA_FOREGROUND, value);
	}

	static brightCyan(value) {
		return Colorizer.format(Colorizer.CYAN_FOREGROUND, value);
	}

	static brightWhite(value) {
		return Colorizer.format(Colorizer.WHITE_FOREGROUND, value);
	}

	static rgbBackground(value, r, g, b) {
		return Colorizer.format(Colorizer.RGB_BACKGROUND.plus0(FlatString.construct5(";2;").plus0(FlatInt.toString((r)).plus0(FlatString.construct5(";").plus0(FlatInt.toString((g)).plus0(FlatString.construct5(";").plus0(FlatInt.toString((b)).plus0(FlatString.construct5("")))))))), value);
	}

	static background256(value, num) {
		return Colorizer.format(Colorizer.RGB_BACKGROUND.plus0(FlatString.construct5(";5;").plus0(FlatInt.toString((num)).plus0(FlatString.construct5("")))), value);
	}

	static blackBackground(value) {
		return Colorizer.format(Colorizer.BLACK_BACKGROUND, value);
	}

	static redBackground(value) {
		return Colorizer.format(Colorizer.RED_BACKGROUND, value);
	}

	static greenBackground(value) {
		return Colorizer.format(Colorizer.GREEN_BACKGROUND, value);
	}

	static yellowBackground(value) {
		return Colorizer.format(Colorizer.YELLOW_BACKGROUND, value);
	}

	static blueBackground(value) {
		return Colorizer.format(Colorizer.BLUE_BACKGROUND, value);
	}

	static magentaBackground(value) {
		return Colorizer.format(Colorizer.MAGENTA_BACKGROUND, value);
	}

	static cyanBackground(value) {
		return Colorizer.format(Colorizer.CYAN_BACKGROUND, value);
	}

	static whiteBackground(value) {
		return Colorizer.format(Colorizer.WHITE_BACKGROUND, value);
	}

	static grayBackground(value) {
		return Colorizer.format(Colorizer.GRAY_BACKGROUND, value);
	}

	static brightBlackBackground(value) {
		return Colorizer.format(Colorizer.GRAY_BACKGROUND, value);
	}

	static brightRedBackground(value) {
		return Colorizer.format(Colorizer.RED_BACKGROUND, value);
	}

	static brightGreenBackground(value) {
		return Colorizer.format(Colorizer.GREEN_BACKGROUND, value);
	}

	static brightYellowBackground(value) {
		return Colorizer.format(Colorizer.YELLOW_BACKGROUND, value);
	}

	static brightBlueBackground(value) {
		return Colorizer.format(Colorizer.BLUE_BACKGROUND, value);
	}

	static brightMagentaBackground(value) {
		return Colorizer.format(Colorizer.MAGENTA_BACKGROUND, value);
	}

	static brightCyanBackground(value) {
		return Colorizer.format(Colorizer.CYAN_BACKGROUND, value);
	}

	static brightWhiteBackground(value) {
		return Colorizer.format(Colorizer.WHITE_BACKGROUND, value);
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated203(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44, value45, value46, value47) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		temp[41] = value41;
		temp[42] = value42;
		temp[43] = value43;
		temp[44] = value44;
		temp[45] = value45;
		temp[46] = value46;
		temp[47] = value47;
		return FlatArray.construct1(temp, 48);
	}

	static accessor__js_class() {
		return typeof Colorizer.__lazy_accessor__js_class !== 'undefined' ? Colorizer.__lazy_accessor__js_class : (Colorizer.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/colorizer/Colorizer"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), Colorizer.generated203(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("format")), Function.construct(FlatString.construct5("reset")), Function.construct(FlatString.construct5("bright")), Function.construct(FlatString.construct5("dim")), Function.construct(FlatString.construct5("underscore")), Function.construct(FlatString.construct5("blink")), Function.construct(FlatString.construct5("reverse")), Function.construct(FlatString.construct5("hidden")), Function.construct(FlatString.construct5("rgb")), Function.construct(FlatString.construct5("foreground256")), Function.construct(FlatString.construct5("black")), Function.construct(FlatString.construct5("red")), Function.construct(FlatString.construct5("green")), Function.construct(FlatString.construct5("yellow")), Function.construct(FlatString.construct5("blue")), Function.construct(FlatString.construct5("magenta")), Function.construct(FlatString.construct5("cyan")), Function.construct(FlatString.construct5("white")), Function.construct(FlatString.construct5("gray")), Function.construct(FlatString.construct5("brightBlack")), Function.construct(FlatString.construct5("brightRed")), Function.construct(FlatString.construct5("brightGreen")), Function.construct(FlatString.construct5("brightYellow")), Function.construct(FlatString.construct5("brightBlue")), Function.construct(FlatString.construct5("brightMagenta")), Function.construct(FlatString.construct5("brightCyan")), Function.construct(FlatString.construct5("brightWhite")), Function.construct(FlatString.construct5("rgbBackground")), Function.construct(FlatString.construct5("background256")), Function.construct(FlatString.construct5("blackBackground")), Function.construct(FlatString.construct5("redBackground")), Function.construct(FlatString.construct5("greenBackground")), Function.construct(FlatString.construct5("yellowBackground")), Function.construct(FlatString.construct5("blueBackground")), Function.construct(FlatString.construct5("magentaBackground")), Function.construct(FlatString.construct5("cyanBackground")), Function.construct(FlatString.construct5("whiteBackground")), Function.construct(FlatString.construct5("grayBackground")), Function.construct(FlatString.construct5("brightBlackBackground")), Function.construct(FlatString.construct5("brightRedBackground")), Function.construct(FlatString.construct5("brightGreenBackground")), Function.construct(FlatString.construct5("brightYellowBackground")), Function.construct(FlatString.construct5("brightBlueBackground")), Function.construct(FlatString.construct5("brightMagentaBackground")), Function.construct(FlatString.construct5("brightCyanBackground")), Function.construct(FlatString.construct5("brightWhiteBackground")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class FlatConsole extends FlatObject {
	static out = flat_null;
	static warn = flat_null;
	static error = flat_null;

	static construct() {
		let __value = new FlatConsole();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatConsole.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatConsole.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static log(obj) {
		obj = typeof obj === 'undefined' ? FlatString.construct5("") : obj;
		return FlatConsole.out.writeLine(obj.toString());
	}

	static writeLine0() {
		return FlatConsole.out.writeLine(FlatString.construct5(""));
	}

	static writeLine1(text) {
		return FlatConsole.out.writeLine(text);
	}

	static writeLine2(obj) {
		return FlatConsole.out.writeLine(obj.toString());
	}

	static writeLine3(num) {
		return FlatConsole.out.writeLine(FlatDouble.toString(num));
	}

	static writeLine4(num) {
		return FlatConsole.out.writeLine(FlatDouble.toString(num));
	}

	static writeLine5(num) {
		return FlatConsole.out.writeLine(FlatLong.toString(num));
	}

	static writeLine6(num) {
		return FlatConsole.out.writeLine(FlatInt.toString(num));
	}

	static writeLine7(num) {
		return FlatConsole.out.writeLine(FlatInt.toString(num));
	}

	static writeLine8(num) {
		return FlatConsole.out.writeLine(FlatByte.toString(num));
	}

	static writeLine9(c) {
		return FlatConsole.out.writeLine(Char.toString(c));
	}

	static write0(text) {
		return FlatConsole.out.write(text);
	}

	static write1(obj) {
		return FlatConsole.out.write(obj.toString());
	}

	static write2(num) {
		return FlatConsole.out.write(FlatDouble.toString(num));
	}

	static write3(num) {
		return FlatConsole.out.write(FlatDouble.toString(num));
	}

	static write4(num) {
		return FlatConsole.out.write(FlatLong.toString(num));
	}

	static write5(num) {
		return FlatConsole.out.write(FlatInt.toString(num));
	}

	static write6(num) {
		return FlatConsole.out.write(FlatInt.toString(num));
	}

	static write7(num) {
		return FlatConsole.out.write(FlatByte.toString(num));
	}

	static write8(c) {
		return FlatConsole.out.write(Char.toString(c));
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated10(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated225(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		return FlatArray.construct1(temp, 22);
	}

	static accessor__js_class() {
		return typeof FlatConsole.__lazy_accessor__js_class !== 'undefined' ? FlatConsole.__lazy_accessor__js_class : (FlatConsole.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/Console"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatConsole.generated10(Field.construct(FlatString.construct5("out")), Field.construct(FlatString.construct5("warn")), Field.construct(FlatString.construct5("error"))), FlatConsole.generated225(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("log")), Function.construct(FlatString.construct5("writeLine")), Function.construct(FlatString.construct5("writeLine")), Function.construct(FlatString.construct5("writeLine")), Function.construct(FlatString.construct5("writeLine")), Function.construct(FlatString.construct5("writeLine")), Function.construct(FlatString.construct5("writeLine")), Function.construct(FlatString.construct5("writeLine")), Function.construct(FlatString.construct5("writeLine")), Function.construct(FlatString.construct5("writeLine")), Function.construct(FlatString.construct5("writeLine")), Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class OutputStream {
	static EMPTY = flat_null;

	write(value) {
		value = typeof value === 'undefined' ? FlatString.construct5("") : value;
		return this;
	}

	writeLine(value) {
		value = typeof value === 'undefined' ? FlatString.construct5("") : value;
		return this.write(value.plus0(FlatString.construct5("\n")));
	}

	close() {
		return false;
	}

	flush() {
		return false;
	}

	async captureOutput(action, silent) {
		silent = typeof silent === 'undefined' ? true : silent;
		return FlatArray.construct();
	}

	static generated125(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated137(value0, value1, value2, value3, value4) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		return FlatArray.construct1(temp, 5);
	}

	static accessor__js_class() {
		return typeof OutputStream.__lazy_accessor__js_class !== 'undefined' ? OutputStream.__lazy_accessor__js_class : (OutputStream.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/OutputStream"), true, FlatObject.accessor__js_class(), FlatArray.construct(), OutputStream.generated125(Field.construct(FlatString.construct5("EMPTY"))), OutputStream.generated137(Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("writeLine")), Function.construct(FlatString.construct5("close")), Function.construct(FlatString.construct5("flush")), Function.construct(FlatString.construct5("captureOutput"))), this);
			})());
	}
}

class ConsoleOutputStream extends FlatObject {
	name = flat_null;
	buffer = flat_null;

	static construct() {
		let __value = new ConsoleOutputStream();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		ConsoleOutputStream.__assignments.apply(__value, [].slice.call(arguments));
		__value = ConsoleOutputStream.__init.call(__value);

		return __value;
	}

	static construct0(name) {
		let __value = new ConsoleOutputStream();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		ConsoleOutputStream.__assignments.apply(__value, [].slice.call(arguments));
		__value = ConsoleOutputStream.__init0.call(__value, name);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		return self;
	}

	static __init0(name) {
		let self = this;

		this.name = name;
		return self;
	}

	write(data) {
		data = typeof data === 'undefined' ? FlatString.construct5("") : data;
		let newLines = data.howMany('\n');
		if (newLines > 1) {
			let index = data.lastIndexOf2(FlatString.construct5("\n"), undefined, undefined);
			let dataToPrint = this.buffer.plus0(data.substring(0, index));
			this.buffer = data.substring(index, undefined);
			console[this.name.chars.data](dataToPrint.chars.data);
		} else {
			this.buffer = this.buffer.concat(data);
		}
		return this;
	}

	writeLine(data) {
		data = typeof data === 'undefined' ? FlatString.construct5("") : data;
		if (this.buffer.accessor_isNotEmpty()) {
			this.buffer = this.buffer.concat(data);
			this.flush();
		} else {
			console[this.name.chars.data](data.chars.data);
		}
		return this;
	}

	async captureOutput(action, silent) {
		let data = flat_null;
		silent = typeof silent === 'undefined' ? true : silent;
		let output = FlatArray.construct();
		let oldBuffer = this.buffer;
		this.buffer = FlatString.construct5("");
		var originalLog = console[this.name.chars.data];
		var log = [];
		if (silent) {
			console[this.name.chars.data] = function () {
				log.push([].slice.call(arguments));
			};
		} else {
			console[this.name.chars.data] = function () {
				originalLog.apply(this, [].slice.call(arguments));
				log.push([].slice.call(arguments));
			};
		}
		try {
			(await action());
		} finally {
			console[this.name.chars.data] = originalLog;
			log.forEach((line) => {
					data = line.map(o => o.toString()).join(" ");
					output.add0(FlatString.construct4(data));
			});
			if (this.buffer.count > 0) {
				data = this.buffer.chars.data;
				output.add0(FlatString.construct4(data));
			}
			this.buffer = oldBuffer;
		}
		return output;
	}

	flush() {
		if (this.buffer.count > 0) {
			console[this.name.chars.data](this.buffer.chars.data);
			this.buffer = FlatString.construct5("");
		}
		return true;
	}

	close() {
		return false;
	}

	static generated2(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated43(value0, value1, value2, value3, value4, value5, value6, value7, value8) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		return FlatArray.construct1(temp, 9);
	}

	static accessor__js_class() {
		return typeof ConsoleOutputStream.__lazy_accessor__js_class !== 'undefined' ? ConsoleOutputStream.__lazy_accessor__js_class : (ConsoleOutputStream.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/ConsoleOutputStream"), false, FlatObject.accessor__js_class(), ConsoleOutputStream.generated2(OutputStream.accessor__js_class()), FlatArray.construct(), ConsoleOutputStream.generated43(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("writeLine")), Function.construct(FlatString.construct5("captureOutput")), Function.construct(FlatString.construct5("flush")), Function.construct(FlatString.construct5("close"))), this);
			})());
	}

	static __assignments() {
		this.buffer = FlatString.construct5("");
	}
}

class Timer extends FlatObject {
	startTime = 0;
	endTime = 0;

	static construct() {
		let __value = new Timer();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Timer.__assignments.apply(__value, [].slice.call(arguments));
		__value = Timer.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		return self;
	}

	start() {
		this.startTime = Time.accessor_currentTimeMillis();
		this.endTime = 0;
		return this;
	}

	stop() {
		this.endTime = Time.accessor_currentTimeMillis();
		return this;
	}

	reset() {
		this.startTime = 0;
		this.endTime = 0;
		return this;
	}

	toString() {
		return FlatLong.toString((this.accessor_duration())).plus0(FlatString.construct5("ms"));
	}

	static time(action, callback) {
		callback = typeof callback === 'undefined' ? (_1) => {
		} : callback;
		let t = Timer.construct().start();
		action();
		callback(t.stop());
		return t;
	}

	static generated217(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated232(value0, value1, value2, value3, value4, value5, value6) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		return FlatArray.construct1(temp, 7);
	}

	static accessor__js_class() {
		return typeof Timer.__lazy_accessor__js_class !== 'undefined' ? Timer.__lazy_accessor__js_class : (Timer.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/time/Timer"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Timer.generated217(Field.construct(FlatString.construct5("startTime")), Field.construct(FlatString.construct5("endTime")), Field.construct(FlatString.construct5("duration"))), Timer.generated232(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("start")), Function.construct(FlatString.construct5("stop")), Function.construct(FlatString.construct5("reset")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("time"))), this);
			})());
	}

	accessor_duration() {
		return this.endTime - this.startTime;
	}

	static __assignments() {
	}
}

class CumulativeTimer extends Timer {
	duration = 0;
	iterations = 0;

	static construct() {
		let __value = new CumulativeTimer();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Timer.__assignments.apply(__value, [].slice.call(arguments));
		CumulativeTimer.__assignments.apply(__value, [].slice.call(arguments));
		__value = CumulativeTimer.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		self = Timer.__init.call(this);
		return self;
	}

	stop() {
		super.stop();
		this.iterations++;
		this.mutator_duration(this.accessor_duration() + super.accessor_duration());
		return this;
	}

	static generated29(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated40(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static accessor__js_class() {
		return typeof CumulativeTimer.__lazy_accessor__js_class !== 'undefined' ? CumulativeTimer.__lazy_accessor__js_class : (CumulativeTimer.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/time/CumulativeTimer"), false, Timer.accessor__js_class(), FlatArray.construct(), CumulativeTimer.generated29(Field.construct(FlatString.construct5("duration")), Field.construct(FlatString.construct5("iterations"))), CumulativeTimer.generated40(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("stop"))), this);
			})());
	}

	accessor_duration() {
		return this.duration;
	}

	mutator_duration(value) {
		this.duration = value;
		return value;
	}

	static __assignments() {
		this.iterations = 0;
	}
}

class Curl extends FlatObject {
	static construct() {
		let __value = new Curl();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Curl.__assignments.apply(__value, [].slice.call(arguments));
		__value = Curl.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated16(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof Curl.__lazy_accessor__js_class !== 'undefined' ? Curl.__lazy_accessor__js_class : (Curl.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/Curl"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), Curl.generated16(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class FlatDate extends FlatObject {
	year = 0;
	month = 0;
	day = 0;
	hour = 0;
	minute = 0;
	second = 0;

	static construct() {
		let __value = new FlatDate();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatDate.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatDate.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		this.updateTime();
		return self;
	}

	decodeDate(prototype, date) {
	}

	updateTime() {
	}

	formatDate(str, first, second, third, fourth, fifth, sixth) {
		first = typeof first === 'undefined' ? this.month : first;
		second = typeof second === 'undefined' ? this.day : second;
		third = typeof third === 'undefined' ? this.year : third;
		fourth = typeof fourth === 'undefined' ? this.hour : fourth;
		fifth = typeof fifth === 'undefined' ? this.minute : fifth;
		sixth = typeof sixth === 'undefined' ? second : sixth;
		return flat_null;
	}

	static generated19(value0, value1, value2, value3, value4, value5) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		return FlatArray.construct1(temp, 6);
	}

	static generated218(value0, value1, value2, value3, value4) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		return FlatArray.construct1(temp, 5);
	}

	static accessor__js_class() {
		return typeof FlatDate.__lazy_accessor__js_class !== 'undefined' ? FlatDate.__lazy_accessor__js_class : (FlatDate.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/time/Date"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatDate.generated19(Field.construct(FlatString.construct5("year")), Field.construct(FlatString.construct5("month")), Field.construct(FlatString.construct5("day")), Field.construct(FlatString.construct5("hour")), Field.construct(FlatString.construct5("minute")), Field.construct(FlatString.construct5("second"))), FlatDate.generated218(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("decodeDate")), Function.construct(FlatString.construct5("updateTime")), Function.construct(FlatString.construct5("formatDate"))), this);
			})());
	}

	static __assignments() {
	}
}

class GreaterThanOrEqualToOperator {
	static generated223(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof GreaterThanOrEqualToOperator.__lazy_accessor__js_class !== 'undefined' ? GreaterThanOrEqualToOperator.__lazy_accessor__js_class : (GreaterThanOrEqualToOperator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/operators/GreaterThanOrEqualToOperator"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), GreaterThanOrEqualToOperator.generated223(Function.construct(FlatString.construct5("greaterThanOrEqualTo"))), this);
			})());
	}
}

class GreaterThanOperator {
	static generated220(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof GreaterThanOperator.__lazy_accessor__js_class !== 'undefined' ? GreaterThanOperator.__lazy_accessor__js_class : (GreaterThanOperator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/operators/GreaterThanOperator"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), GreaterThanOperator.generated220(Function.construct(FlatString.construct5("greaterThan"))), this);
			})());
	}
}

class LessThanOrEqualToOperator {
	static generated76(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof LessThanOrEqualToOperator.__lazy_accessor__js_class !== 'undefined' ? LessThanOrEqualToOperator.__lazy_accessor__js_class : (LessThanOrEqualToOperator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/operators/LessThanOrEqualToOperator"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), LessThanOrEqualToOperator.generated76(Function.construct(FlatString.construct5("lessThanOrEqualTo"))), this);
			})());
	}
}

class LessThanOperator {
	static generated75(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof LessThanOperator.__lazy_accessor__js_class !== 'undefined' ? LessThanOperator.__lazy_accessor__js_class : (LessThanOperator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/operators/LessThanOperator"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), LessThanOperator.generated75(Function.construct(FlatString.construct5("lessThan"))), this);
			})());
	}
}

class FlatDateTime extends FlatObject {
	jsDate = flat_null;
	millis = 0;
	year = 0;
	month = 0;
	dayOfMonth = 0;
	dayOfYear = 0;
	hour = 0;
	minute = 0;
	second = 0;
	static SECS_DAY = 0;
	static SECS_HOUR = 0;
	static SECS_MINUTE = 0;
	static EPOCH_YEAR = 0;
	static MONTH_DAYS = flat_null;
	static LEAP_MONTH_DAYS = flat_null;

	static construct0(input, format) {
		let __value = new FlatDateTime();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatDateTime.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatDateTime.__init0.call(__value, input, format);

		return __value;
	}

	static construct1(millis) {
		let __value = new FlatDateTime();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatDateTime.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatDateTime.__init3.call(__value, millis);

		return __value;
	}

	static construct2(year, month, dayOfMonth, hour, minute, second, millisecond) {
		let __value = new FlatDateTime();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatDateTime.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatDateTime.__init1.call(__value, year, month, dayOfMonth, hour, minute, second, millisecond);

		return __value;
	}

	static construct3(dateTime) {
		let __value = new FlatDateTime();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatDateTime.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatDateTime.__init2.call(__value, dateTime);

		return __value;
	}

	destroy() {
	}

	static __init0(input, format) {
		let self = this;

		format = typeof format === 'undefined' ? flat_null : format;
		if (format === flat_null) {
			throw Exception.construct(FlatString.construct5("implicit formats not implemented yet"));
		}
		Regex.getMatches1(FlatString.construct5("yyyy"), format).forEach1((match, _i, _array) => {
				self.year = FlatInt.parseInt(input.substring(match.start, match.end));
		});
		Regex.getMatches1(FlatString.construct5("MM"), format).forEach1((match, _i, _array) => {
				self.month = FlatInt.parseInt(input.substring(match.start, match.end));
		});
		Regex.getMatches1(FlatString.construct5("dd"), format).forEach1((match, _i, _array) => {
				self.dayOfMonth = FlatInt.parseInt(input.substring(match.start, match.end));
		});
		self = FlatDateTime.__init1.call(this, this.year, this.month, this.dayOfMonth);
		return self;
	}

	static __init1(year, month, dayOfMonth, hour, minute, second, millisecond) {
		let self = this;

		year = typeof year === 'undefined' ? 0 : year;
		month = typeof month === 'undefined' ? 1 : month;
		dayOfMonth = typeof dayOfMonth === 'undefined' ? 1 : dayOfMonth;
		hour = typeof hour === 'undefined' ? 0 : hour;
		minute = typeof minute === 'undefined' ? 0 : minute;
		second = typeof second === 'undefined' ? 0 : second;
		millisecond = typeof millisecond === 'undefined' ? 0 : millisecond;
		this.second = second;
		this.minute = minute;
		this.hour = hour;
		this.dayOfMonth = dayOfMonth;
		this.month = month;
		this.year = year;
		if (month <= 0 || month > 12) {
			throw DateTime_InvalidDateException.construct(FlatString.construct5("Invalid month ").plus0(FlatInt.toString((month)).plus0(FlatString.construct5(". Must be 1-12"))));
		} else if (!DateTime_Calculator.isValidDayOfMonth(year, month, dayOfMonth)) {
			throw DateTime_InvalidDateException.construct(FlatString.construct5("Invalid day of month ").plus0(FlatInt.toString((dayOfMonth)).plus0(FlatString.construct5(" for month ").plus0(FlatInt.toString((month)).plus0(FlatString.construct5(""))))));
		} else if (month === 2 && dayOfMonth === 29 && !this.accessor_isLeapYear()) {
			throw DateTime_InvalidDateException.construct(FlatString.construct5("Year ").plus0(FlatInt.toString((year)).plus0(FlatString.construct5(" is not a leap year"))));
		}
		self = FlatDateTime.__init3.call(this, this.calculateMillis(undefined, undefined, undefined, undefined, undefined, undefined, undefined) + millisecond);
		this.year = year;
		this.month = month;
		this.dayOfMonth = dayOfMonth;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		return self;
	}

	calculateMillis(year, month, dayOfMonth, hour, minute, second, millisecond) {
		year = typeof year === 'undefined' ? this.year : year;
		month = typeof month === 'undefined' ? this.month : month;
		dayOfMonth = typeof dayOfMonth === 'undefined' ? this.dayOfMonth : dayOfMonth;
		hour = typeof hour === 'undefined' ? this.hour : hour;
		minute = typeof minute === 'undefined' ? this.minute : minute;
		second = typeof second === 'undefined' ? this.second : second;
		millisecond = typeof millisecond === 'undefined' ? this.accessor_millisecond() : millisecond;
		return DateTime_Calculator.getYearMillis(year) + DateTime_Calculator.getMonthMillis(year, month) + DateTime_Calculator.getDayMillis(dayOfMonth) + DateTime_Calculator.getHourMillis(hour) + DateTime_Calculator.getMinuteMillis(minute) + DateTime_Calculator.getSecondMillis(second) + DateTime_Calculator.getMillisecondMillis(millisecond);
	}

	static __init2(dateTime) {
		let self = this;

		self = FlatDateTime.__init3.call(this, dateTime.millis);
		return self;
	}

	addMonth() {
		this.addMonths(1);
	}

	addMonths(months) {
		let flat_local_0 = flat_null;
		let flat_local_1 = flat_null;
		let flat_local_2 = flat_null;
		if (months > 0) {
			this.year += ~~((months + this.month - 1) / 12);
			this.month = ~~((this.month - 1 + months) % 12);
		} else {
			this.month = this.month - 1 + months;
			while (this.month < 0) {
				this.month = this.month + 12;
				this.year--;
			}
		}
		++this.month;
		let days = DateTime_Calculator.getMonthDays(this.year).get(this.month - 1);
		if (this.dayOfMonth > ((flat_local_2 = (days)) !== flat_null ? (flat_local_2.value) : 0)) {
			this.dayOfYear -= this.dayOfMonth - ((flat_local_0 = (days)) !== flat_null ? (flat_local_0.value) : 0);
			this.dayOfMonth = ((flat_local_1 = (days)) !== flat_null ? (flat_local_1.value) : 0);
		}
		this.millis = this.calculateMillis(undefined, undefined, undefined, undefined, undefined, undefined, undefined);
	}

	subtractMonth() {
		this.subtractMonths(1);
	}

	subtractMonths(months) {
		this.addMonths(-months);
	}

	addYear() {
		this.addYears(1);
	}

	addYears(years) {
		this.year += years;
		if (this.month === 2 && this.dayOfMonth === 29 && !DateTime_Calculator.isLeapYear(this.year)) {
			this.dayOfMonth--;
			this.dayOfYear--;
		}
		this.millis = this.calculateMillis(undefined, undefined, undefined, undefined, undefined, undefined, undefined);
	}

	subtractYear() {
		this.subtractYears(1);
	}

	subtractYears(years) {
		this.addYears(-years);
	}

	compareTo0(other) {
		return (this.millis - other.millis);
	}

	lessThan0(other) {
		return this.millis < other.millis;
	}

	lessThanOrEqualTo0(other) {
		return this.millis <= other.millis;
	}

	greaterThan0(other) {
		return this.millis > other.millis;
	}

	greaterThanOrEqualTo0(other) {
		return this.millis >= other.millis;
	}

	toString() {
		return this.toString0(FlatString.construct5("MM/dd/yyyy"));
	}

	toString0(format) {
		let monthString = (this.month < 10 ? FlatString.construct5("0") : FlatString.construct5("")).plus0(FlatInt.toString(this.month));
		let dayOfMonthString = (this.dayOfMonth < 10 ? FlatString.construct5("0") : FlatString.construct5("")).plus0(FlatInt.toString(this.dayOfMonth));
		return FlatInt.toString((this.year)).plus0(FlatString.construct5("-").plus0((monthString).plus0(FlatString.construct5("-").plus0((dayOfMonthString).plus0(FlatString.construct5(""))))));
	}

	static now() {
		let millis = 0;
		millis = Date.now();
		return FlatDateTime.construct1(millis);
	}

	toIsoString() {
		let isoString = flat_null;
		isoString = this.jsDate.toISOString();
		return FlatString.construct4(isoString);
	}

	static __init3(millis) {
		let self = this;

		let flat_local_0 = flat_null;
		let flat_local_1 = flat_null;
		let flat_local_2 = flat_null;
		let flat_local_3 = flat_null;
		this.millis = millis;
		let yearAndDay = DateTime_Calculator.getYearAndDay(millis);
		this.year = ((flat_local_0 = (yearAndDay.accessor_first())) !== flat_null ? (flat_local_0.value) : 0);
		this.dayOfYear = ((flat_local_1 = (yearAndDay.accessor_last())) !== flat_null ? (flat_local_1.value) : 0);
		let monthAndDay = DateTime_Calculator.getMonthAndDay(this.year, this.dayOfYear);
		this.month = ((flat_local_2 = (monthAndDay.accessor_first())) !== flat_null ? (flat_local_2.value) : 0);
		this.dayOfMonth = ((flat_local_3 = (monthAndDay.accessor_last())) !== flat_null ? (flat_local_3.value) : 0);
		this.hour = DateTime_Calculator.getHour(millis);
		this.minute = DateTime_Calculator.getMinute(millis);
		this.second = DateTime_Calculator.getSecond(millis);
		this.jsDate = new Date(millis);
		this.millis = millis;
		return self;
	}

	static generated20(value0, value1, value2, value3, value4) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		return FlatArray.construct1(temp, 5);
	}

	static generated236(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		return FlatArray.construct1(temp, 16);
	}

	static generated242(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		return FlatArray.construct1(temp, 26);
	}

	static generated264() {
		let temp = flat_null;
		temp = [];
		temp[0] = FlatByte.construct(31);
		temp[1] = FlatByte.construct(28);
		temp[2] = FlatByte.construct(31);
		temp[3] = FlatByte.construct(30);
		temp[4] = FlatByte.construct(31);
		temp[5] = FlatByte.construct(30);
		temp[6] = FlatByte.construct(31);
		temp[7] = FlatByte.construct(31);
		temp[8] = FlatByte.construct(30);
		temp[9] = FlatByte.construct(31);
		temp[10] = FlatByte.construct(30);
		temp[11] = FlatByte.construct(31);
		return FlatArray.construct1(temp, 12);
	}

	static generated265() {
		let temp = flat_null;
		temp = [];
		temp[0] = FlatByte.construct(31);
		temp[1] = FlatByte.construct(29);
		temp[2] = FlatByte.construct(31);
		temp[3] = FlatByte.construct(30);
		temp[4] = FlatByte.construct(31);
		temp[5] = FlatByte.construct(30);
		temp[6] = FlatByte.construct(31);
		temp[7] = FlatByte.construct(31);
		temp[8] = FlatByte.construct(30);
		temp[9] = FlatByte.construct(31);
		temp[10] = FlatByte.construct(30);
		temp[11] = FlatByte.construct(31);
		return FlatArray.construct1(temp, 12);
	}

	static accessor__js_class() {
		return typeof FlatDateTime.__lazy_accessor__js_class !== 'undefined' ? FlatDateTime.__lazy_accessor__js_class : (FlatDateTime.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/time/DateTime"), false, FlatObject.accessor__js_class(), FlatDateTime.generated20(Comparable.accessor__js_class(), LessThanOperator.accessor__js_class(), LessThanOrEqualToOperator.accessor__js_class(), GreaterThanOperator.accessor__js_class(), GreaterThanOrEqualToOperator.accessor__js_class()), FlatDateTime.generated236(Field.construct(FlatString.construct5("millis")), Field.construct(FlatString.construct5("year")), Field.construct(FlatString.construct5("month")), Field.construct(FlatString.construct5("dayOfMonth")), Field.construct(FlatString.construct5("dayOfYear")), Field.construct(FlatString.construct5("hour")), Field.construct(FlatString.construct5("minute")), Field.construct(FlatString.construct5("second")), Field.construct(FlatString.construct5("millisecond")), Field.construct(FlatString.construct5("isLeapYear")), Field.construct(FlatString.construct5("SECS_DAY")), Field.construct(FlatString.construct5("SECS_HOUR")), Field.construct(FlatString.construct5("SECS_MINUTE")), Field.construct(FlatString.construct5("EPOCH_YEAR")), Field.construct(FlatString.construct5("MONTH_DAYS")), Field.construct(FlatString.construct5("LEAP_MONTH_DAYS"))), FlatDateTime.generated242(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("calculateMillis")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("addMonth")), Function.construct(FlatString.construct5("addMonths")), Function.construct(FlatString.construct5("subtractMonth")), Function.construct(FlatString.construct5("subtractMonths")), Function.construct(FlatString.construct5("addYear")), Function.construct(FlatString.construct5("addYears")), Function.construct(FlatString.construct5("subtractYear")), Function.construct(FlatString.construct5("subtractYears")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("lessThan")), Function.construct(FlatString.construct5("lessThanOrEqualTo")), Function.construct(FlatString.construct5("greaterThan")), Function.construct(FlatString.construct5("greaterThanOrEqualTo")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("now")), Function.construct(FlatString.construct5("toIsoString")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor_millisecond() {
		return (~~(this.millis % 1000));
	}

	accessor_isLeapYear() {
		return DateTime_Calculator.isLeapYear(this.year);
	}

	static __assignments() {
	}
	static DateTime_InvalidDateException
	static DateTime_InvalidDateFormatException
	static DateTime_Calculator
}

class DateTime_InvalidDateException extends Exception {
	static construct(message) {
		let __value = new DateTime_InvalidDateException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		DateTime_InvalidDateException.__assignments.apply(__value, [].slice.call(arguments));
		__value = DateTime_InvalidDateException.__init.call(__value, message);

		return __value;
	}

	destroy() {
	}

	static __init(message) {
		let self = this;

		message = typeof message === 'undefined' ? flat_null : message;
		self = Exception.__init.call(this, message);
		return self;
	}

	static generated243(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof DateTime_InvalidDateException.__lazy_accessor__js_class !== 'undefined' ? DateTime_InvalidDateException.__lazy_accessor__js_class : (DateTime_InvalidDateException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/time/DateTime.InvalidDateException"), false, Exception.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), DateTime_InvalidDateException.generated243(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class DateTime_InvalidDateFormatException extends DateTime_InvalidDateException {
	static construct(message) {
		let __value = new DateTime_InvalidDateFormatException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		DateTime_InvalidDateException.__assignments.apply(__value, [].slice.call(arguments));
		DateTime_InvalidDateFormatException.__assignments.apply(__value, [].slice.call(arguments));
		__value = DateTime_InvalidDateFormatException.__init.call(__value, message);

		return __value;
	}

	destroy() {
	}

	static __init(message) {
		let self = this;

		message = typeof message === 'undefined' ? flat_null : message;
		self = DateTime_InvalidDateException.__init.call(this, message);
		return self;
	}

	static generated244(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof DateTime_InvalidDateFormatException.__lazy_accessor__js_class !== 'undefined' ? DateTime_InvalidDateFormatException.__lazy_accessor__js_class : (DateTime_InvalidDateFormatException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/time/DateTime.InvalidDateFormatException"), false, FlatDateTime.DateTime_InvalidDateException.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), DateTime_InvalidDateFormatException.generated244(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class DateTime_Calculator extends FlatObject {
	static construct() {
		let __value = new DateTime_Calculator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		DateTime_Calculator.__assignments.apply(__value, [].slice.call(arguments));
		__value = DateTime_Calculator.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static getLeapYearCount(year, inclusive) {
		inclusive = typeof inclusive === 'undefined' ? true : inclusive;
		let sum = 0;
		{
			let i = 1970;
			for (; i < (inclusive ? year + 1 : year); i++) {
				if (DateTime_Calculator.isLeapYear(i)) {
					sum++;
				}
			}
		}
		return sum;
	}

	static getYearMillis(year) {
		--year;
		let millis = 0;
		while (year >= FlatDateTime.EPOCH_YEAR) {
			millis += DateTime_Calculator.getYearSize(year) * 24 * 60 * 60 * 1000;
			--year;
		}
		return millis;
	}

	static getMonthMillis(year, month) {
		--month;
		let millis = 0;
		while (month > 0) {
			let monthDays = DateTime_Calculator.getMonthDays(year).get(month - 1).value;
			millis += monthDays * 24 * 60 * 60 * 1000;
			--month;
		}
		return millis;
	}

	static getDayMillis(dayOfMonth) {
		return (dayOfMonth - 1) * 24 * 60 * 60 * 1000;
	}

	static getHourMillis(hour) {
		return hour * 60 * 60 * 1000;
	}

	static getMinuteMillis(minute) {
		return minute * 60 * 1000;
	}

	static getSecondMillis(second) {
		return second * 1000;
	}

	static getMillisecondMillis(millisecond) {
		return millisecond;
	}

	static getYear(millis) {
		return DateTime_Calculator.getYearAndDay(millis).accessor_first();
	}

	static getDayOfMonth(millis) {
		return DateTime_Calculator.getYearAndDay(millis).accessor_last();
	}

	static getHour(millis) {
		return ~~((~~(millis / (60 * 60 * 1000))) % 24);
	}

	static getMinute(millis) {
		return ~~((~~(millis / (60 * 1000))) % 60);
	}

	static getSecond(millis) {
		return ~~((~~(millis / 1000)) % 60);
	}

	static getMillisecond(millis) {
		return ~~(millis % 1000);
	}

	static getYearSize(year) {
		return DateTime_Calculator.isLeapYear(year) ? 366 : 365;
	}

	static getMonthDays(year) {
		return DateTime_Calculator.isLeapYear(year) ? FlatDateTime.LEAP_MONTH_DAYS : FlatDateTime.MONTH_DAYS;
	}

	static getYearAndDay(millis) {
		let size = 0;
		let year = FlatDateTime.EPOCH_YEAR;
		let day = ~~(millis / FlatDateTime.SECS_DAY);
		while (day >= (size = DateTime_Calculator.getYearSize(year))) {
			day -= size;
			year++;
		}
		return DateTime_Calculator.generated280(year, day + 1);
	}

	static getMonthAndDay(year, yearDay) {
		let flat_local_0 = flat_null;
		let flat_local_1 = flat_null;
		let month = 0;
		let monthDay = yearDay - 1;
		while (monthDay >= ((flat_local_1 = (DateTime_Calculator.getMonthDays(year).get(month))) !== flat_null ? (flat_local_1.value) : 0)) {
			monthDay -= ((flat_local_0 = (DateTime_Calculator.getMonthDays(year).get(month++))) !== flat_null ? (flat_local_0.value) : 0);
		}
		return DateTime_Calculator.generated281(month + 1, monthDay + 1);
	}

	static isLeapYear(year) {
		return (year & 3) === 0 && ((~~(year % 25)) !== 0 || (year & 15) === 0);
	}

	static isValidDayOfMonth(year, month, day) {
		let flat_local_0 = flat_null;
		return day > 0 && day <= ((flat_local_0 = (DateTime_Calculator.getMonthDays(year).get(month - 1))) !== flat_null ? (flat_local_0.value) : 0);
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated245(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		return FlatArray.construct1(temp, 22);
	}

	static generated280(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = FlatInt.construct(value0);
		temp[1] = FlatInt.construct(value1);
		return FlatArray.construct1(temp, 2);
	}

	static generated281(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = FlatInt.construct(value0);
		temp[1] = FlatInt.construct(value1);
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof DateTime_Calculator.__lazy_accessor__js_class !== 'undefined' ? DateTime_Calculator.__lazy_accessor__js_class : (DateTime_Calculator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/time/DateTime.Calculator"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), DateTime_Calculator.generated245(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("getLeapYearCount")), Function.construct(FlatString.construct5("getYearMillis")), Function.construct(FlatString.construct5("getMonthMillis")), Function.construct(FlatString.construct5("getDayMillis")), Function.construct(FlatString.construct5("getHourMillis")), Function.construct(FlatString.construct5("getMinuteMillis")), Function.construct(FlatString.construct5("getSecondMillis")), Function.construct(FlatString.construct5("getMillisecondMillis")), Function.construct(FlatString.construct5("getYear")), Function.construct(FlatString.construct5("getDayOfMonth")), Function.construct(FlatString.construct5("getHour")), Function.construct(FlatString.construct5("getMinute")), Function.construct(FlatString.construct5("getSecond")), Function.construct(FlatString.construct5("getMillisecond")), Function.construct(FlatString.construct5("getYearSize")), Function.construct(FlatString.construct5("getMonthDays")), Function.construct(FlatString.construct5("getYearAndDay")), Function.construct(FlatString.construct5("getMonthAndDay")), Function.construct(FlatString.construct5("isLeapYear")), Function.construct(FlatString.construct5("isValidDayOfMonth")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class DivideByZeroException extends Exception {
	static construct() {
		let __value = new DivideByZeroException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		DivideByZeroException.__assignments.apply(__value, [].slice.call(arguments));
		__value = DivideByZeroException.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		self = Exception.__init.call(this);
		return self;
	}

	static generated21(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof DivideByZeroException.__lazy_accessor__js_class !== 'undefined' ? DivideByZeroException.__lazy_accessor__js_class : (DivideByZeroException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/exception/DivideByZeroException"), false, Exception.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), DivideByZeroException.generated21(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class RealNumber {
	static accessor__js_class() {
		return typeof RealNumber.__lazy_accessor__js_class !== 'undefined' ? RealNumber.__lazy_accessor__js_class : (RealNumber.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/primitive/number/RealNumber"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), FlatArray.construct(), this);
			})());
	}
}

class FlatDouble extends Number {
	value = 0;

	static construct(value) {
		let __value = new FlatDouble();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Primitive.__assignments.apply(__value, [].slice.call(arguments));
		Number.__assignments.apply(__value, [].slice.call(arguments));
		FlatDouble.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatDouble.__init.call(__value, value);

		return __value;
	}

	destroy() {
	}

	static __init(value) {
		let self = this;

		this.value = value;
		return self;
	}

	static numDigits(number) {
		let size = number < 0 ? 2 : 1;
		number /= 10;
		while (number > 0) {
			number /= 10;
			size++;
		}
		return size;
	}

	static toJson(value, format, tab) {
		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		return FlatDouble.toString(value);
	}

	withinTolerance(target, tolerance) {
		return FlatDouble.withinTolerance(this.value, target, tolerance);
	}

	static withinTolerance(value, target, tolerance) {
		tolerance = typeof tolerance === 'undefined' ? 0.00000001 : tolerance;
		return (value - target) < tolerance && (value - target) > -tolerance;
	}

	compareTo0(other) {
		let flat_local_0 = flat_null;
		let flat_local_1 = flat_null;
		return this.value > ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0) ? 1 : (this.value === ((flat_local_1 = (other)) !== flat_null ? (flat_local_1.value) : 0) ? 0 : -1);
	}

	compareToReal(other) {
		return this.value > other ? 1 : (this.value === other ? 0 : -1);
	}

	compareToInteger(other) {
		return this.value > other ? 1 : (this.value === other ? 0 : -1);
	}

	static compareTo(value, other) {
		return value - other;
	}

	static compareToReal(value, other) {
		return value - other;
	}

	static compareToInteger(value, other) {
		return (value - other);
	}

	plus0(other) {
		let flat_local_0 = flat_null;
		return FlatDouble.construct(this.value + ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	plusReal(other) {
		return this.value + other;
	}

	plusInteger(other) {
		return (this.value + other);
	}

	plusEquals0(other) {
		let flat_local_0 = flat_null;
		return FlatDouble.construct(this.value += ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	plusEqualsReal(other) {
		return this.value += other;
	}

	plusEqualsInteger(other) {
		return (this.value += other);
	}

	minus(other) {
		let flat_local_0 = flat_null;
		return FlatDouble.construct(this.value - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	minusReal(other) {
		return this.value - other;
	}

	minusInteger(other) {
		return (this.value - other);
	}

	minusEquals(other) {
		let flat_local_0 = flat_null;
		return FlatDouble.construct(this.value -= ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	minusEqualsReal(other) {
		return this.value -= other;
	}

	minusEqualsInteger(other) {
		return (this.value -= other);
	}

	multiply0(other) {
		let flat_local_0 = flat_null;
		return FlatDouble.construct(this.value * ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	multiplyReal(other) {
		return this.value * other;
	}

	multiplyInteger(other) {
		return (this.value * other);
	}

	multiplyEquals(other) {
		let flat_local_0 = flat_null;
		return FlatDouble.construct(this.value *= ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	multiplyEqualsReal(other) {
		return this.value *= other;
	}

	multiplyEqualsInteger(other) {
		return (this.value *= other);
	}

	equals(other) {
		let flat_local_0 = flat_null;
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(Number.accessor__js_class()) && ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0) === this.value;
	}

	equals0(other) {
		return this.value === other;
	}

	equalsReal(other) {
		return this.value === other;
	}

	equalsInteger(other) {
		return this.value === other;
	}

	static equals(value, other) {
		return value === other;
	}

	static equalsReal(value, other) {
		return value === other;
	}

	static equalsInteger(value, other) {
		return value === other;
	}

	toString() {
		return FlatDouble.toString(this.value);
	}

	static hashCodeLong(value) {
		return FlatMath.abs0(value);
	}

	static plus(a, b) {
		return a + b;
	}

	static parseDouble(str) {
		return parseFloat(str.chars.data);
	}

	static toCharArray(value) {
		return value.toString();
	}

	static toString(value) {
		return FlatString.construct4(FlatDouble.toCharArray(value));
	}

	toFixed(precision) {
		return FlatDouble.toFixed(this.value, precision);
	}

	static toFixed(value, precision) {
		let data = flat_null;
		data = value.toFixed(precision);
		return FlatString.construct4(data);
	}

	static generated26(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated45(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated230(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		temp[41] = value41;
		temp[42] = value42;
		temp[43] = value43;
		temp[44] = value44;
		return FlatArray.construct1(temp, 45);
	}

	static accessor__js_class() {
		return typeof FlatDouble.__lazy_accessor__js_class !== 'undefined' ? FlatDouble.__lazy_accessor__js_class : (FlatDouble.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/primitive/number/Double"), false, Number.accessor__js_class(), FlatDouble.generated26(RealNumber.accessor__js_class()), FlatDouble.generated45(Field.construct(FlatString.construct5("realValue")), Field.construct(FlatString.construct5("integerValue")), Field.construct(FlatString.construct5("value"))), FlatDouble.generated230(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("numDigits")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("withinTolerance")), Function.construct(FlatString.construct5("withinTolerance")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("plusReal")), Function.construct(FlatString.construct5("plusInteger")), Function.construct(FlatString.construct5("plusEquals")), Function.construct(FlatString.construct5("plusEqualsReal")), Function.construct(FlatString.construct5("plusEqualsInteger")), Function.construct(FlatString.construct5("minus")), Function.construct(FlatString.construct5("minusReal")), Function.construct(FlatString.construct5("minusInteger")), Function.construct(FlatString.construct5("minusEquals")), Function.construct(FlatString.construct5("minusEqualsReal")), Function.construct(FlatString.construct5("minusEqualsInteger")), Function.construct(FlatString.construct5("multiply")), Function.construct(FlatString.construct5("multiplyReal")), Function.construct(FlatString.construct5("multiplyInteger")), Function.construct(FlatString.construct5("multiplyEquals")), Function.construct(FlatString.construct5("multiplyEqualsReal")), Function.construct(FlatString.construct5("multiplyEqualsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("hashCodeLong")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("parseDouble")), Function.construct(FlatString.construct5("toCharArray")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toFixed")), Function.construct(FlatString.construct5("toFixed"))), this);
			})());
	}

	accessor_realValue() {
		return this.value;
	}

	accessor_integerValue() {
		return this.value;
	}

	static __assignments() {
	}
}

class EmptyOutputStream extends FlatObject {
	static construct() {
		let __value = new EmptyOutputStream();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		EmptyOutputStream.__assignments.apply(__value, [].slice.call(arguments));
		__value = EmptyOutputStream.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated13(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated33(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof EmptyOutputStream.__lazy_accessor__js_class !== 'undefined' ? EmptyOutputStream.__lazy_accessor__js_class : (EmptyOutputStream.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/EmptyOutputStream"), false, FlatObject.accessor__js_class(), EmptyOutputStream.generated13(OutputStream.accessor__js_class()), FlatArray.construct(), EmptyOutputStream.generated33(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class NoSuchElementException extends Exception {
	static construct(message) {
		let __value = new NoSuchElementException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		NoSuchElementException.__assignments.apply(__value, [].slice.call(arguments));
		__value = NoSuchElementException.__init.call(__value, message);

		return __value;
	}

	destroy() {
	}

	static __init(message) {
		let self = this;

		message = typeof message === 'undefined' ? FlatString.construct5("No such element") : message;
		self = Exception.__init.call(this, message);
		return self;
	}

	static generated118(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof NoSuchElementException.__lazy_accessor__js_class !== 'undefined' ? NoSuchElementException.__lazy_accessor__js_class : (NoSuchElementException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/NoSuchElementException"), false, Exception.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), NoSuchElementException.generated118(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class EmptyStackException extends NoSuchElementException {
	static construct(message) {
		let __value = new EmptyStackException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		NoSuchElementException.__assignments.apply(__value, [].slice.call(arguments));
		EmptyStackException.__assignments.apply(__value, [].slice.call(arguments));
		__value = EmptyStackException.__init.call(__value, message);

		return __value;
	}

	destroy() {
	}

	static __init(message) {
		let self = this;

		message = typeof message === 'undefined' ? FlatString.construct5("") : message;
		self = NoSuchElementException.__init.call(this, message);
		return self;
	}

	static generated28(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof EmptyStackException.__lazy_accessor__js_class !== 'undefined' ? EmptyStackException.__lazy_accessor__js_class : (EmptyStackException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/EmptyStackException"), false, NoSuchElementException.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), EmptyStackException.generated28(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class EscapeCode extends FlatObject {
	static ESCAPE = flat_null;
	static MODE_SUFFIX = flat_null;
	static RESET = flat_null;
	static BRIGHT = flat_null;
	static DIM = flat_null;
	static UNDERSCORE = flat_null;
	static BLINK = flat_null;
	static REVERSE = flat_null;
	static HIDDEN = flat_null;
	static BLACK_FOREGROUND = flat_null;
	static RED_FOREGROUND = flat_null;
	static GREEN_FOREGROUND = flat_null;
	static YELLOW_FOREGROUND = flat_null;
	static BLUE_FOREGROUND = flat_null;
	static MAGENTA_FOREGROUND = flat_null;
	static CYAN_FOREGROUND = flat_null;
	static WHITE_FOREGROUND = flat_null;
	static RGB_FOREGROUND_PREFIX = flat_null;
	static RGB_FOREGROUND_256_PREFIX = flat_null;
	static GRAY_FOREGROUND = flat_null;
	static BRIGHT_BLACK_FOREGROUND = flat_null;
	static BRIGHT_RED_FOREGROUND = flat_null;
	static BRIGHT_GREEN_FOREGROUND = flat_null;
	static BRIGHT_YELLOW_FOREGROUND = flat_null;
	static BRIGHT_BLUE_FOREGROUND = flat_null;
	static BRIGHT_MAGENTA_FOREGROUND = flat_null;
	static BRIGHT_CYAN_FOREGROUND = flat_null;
	static BRIGHT_WHITE_FOREGROUND = flat_null;
	static BLACK_BACKGROUND = flat_null;
	static RED_BACKGROUND = flat_null;
	static GREEN_BACKGROUND = flat_null;
	static YELLOW_BACKGROUND = flat_null;
	static BLUE_BACKGROUND = flat_null;
	static MAGENTA_BACKGROUND = flat_null;
	static CYAN_BACKGROUND = flat_null;
	static WHITE_BACKGROUND = flat_null;
	static RGB_BACKGROUND_PREFIX = flat_null;
	static RGB_BACKGROUND_256_PREFIX = flat_null;
	static GRAY_BACKGROUND = flat_null;
	static BRIGHT_BLACK_BACKGROUND = flat_null;
	static BRIGHT_RED_BACKGROUND = flat_null;
	static BRIGHT_GREEN_BACKGROUND = flat_null;
	static BRIGHT_YELLOW_BACKGROUND = flat_null;
	static BRIGHT_BLUE_BACKGROUND = flat_null;
	static BRIGHT_MAGENTA_BACKGROUND = flat_null;
	static BRIGHT_CYAN_BACKGROUND = flat_null;
	static BRIGHT_WHITE_BACKGROUND = flat_null;
	static CURSOR_MOVE_TO_HOME = flat_null;
	static CURSOR_MOVE_TO_POSITION_PREFIX = flat_null;
	static CURSOR_MOVE_TO_POSITION_SUFFIX = flat_null;
	static CURSOR_MOVE_UP_PREFIX = flat_null;
	static CURSOR_MOVE_UP_SUFFIX = flat_null;
	static CURSOR_MOVE_DOWN_PREFIX = flat_null;
	static CURSOR_MOVE_DOWN_SUFFIX = flat_null;
	static CURSOR_MOVE_RIGHT_PREFIX = flat_null;
	static CURSOR_MOVE_RIGHT_SUFFIX = flat_null;
	static CURSOR_MOVE_LEFT_PREFIX = flat_null;
	static CURSOR_MOVE_LEFT_SUFFIX = flat_null;
	static CURSOR_MOVE_DOWN_AT_BEGINNING_OF_LINE_PREFIX = flat_null;
	static CURSOR_MOVE_DOWN_AT_BEGINNING_OF_LINE_SUFFIX = flat_null;
	static CURSOR_MOVE_UP_AT_BEGINNING_OF_LINE_PREFIX = flat_null;
	static CURSOR_MOVE_UP_AT_BEGINNING_OF_LINE_SUFFIX = flat_null;
	static CURSOR_MOVE_TO_COLUMN_PREFIX = flat_null;
	static CURSOR_MOVE_TO_COLUMN_SUFFIX = flat_null;
	static CURSOR_REQUEST_POSITION = flat_null;
	static CURSOR_MOVE_UP_ONE_LINE = flat_null;
	static CURSOR_SAVE_POSITION = flat_null;
	static CURSOR_RESTORE_POSITION = flat_null;
	static ERASE_FROM_CURSOR_TO_END_OF_SCREEN = flat_null;
	static ERASE_FROM_CURSOR_TO_BEGINNING_OF_SCREEN = flat_null;
	static ERASE_ENTIRE_SCREEN = flat_null;
	static ERASE_SAVED_LINES = flat_null;
	static ERASE_FROM_CURSOR_TO_END_OF_LINE = flat_null;
	static ERASE_FROM_CURSOR_TO_BEGINNING_OF_LINE = flat_null;
	static ERASE_ENTIRE_LINE = flat_null;

	static construct() {
		let __value = new EscapeCode();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		EscapeCode.__assignments.apply(__value, [].slice.call(arguments));
		__value = EscapeCode.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static cursorMoveHome() {
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_TO_HOME).plus0(FlatString.construct5(""))));
	}

	static cursorMoveToPosition(line, column) {
		line = typeof line === 'undefined' ? 0 : line;
		column = typeof column === 'undefined' ? 0 : column;
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_TO_POSITION_PREFIX).plus0(FlatString.construct5("").plus0(FlatInt.toString((line)).plus0(FlatString.construct5(";").plus0(FlatInt.toString((column)).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_TO_POSITION_SUFFIX).plus0(FlatString.construct5(""))))))))));
	}

	static cursorMoveUp(lines) {
		lines = typeof lines === 'undefined' ? 0 : lines;
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_UP_PREFIX).plus0(FlatString.construct5("").plus0(FlatInt.toString((lines)).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_UP_SUFFIX).plus0(FlatString.construct5(""))))))));
	}

	static cursorMoveDown(lines) {
		lines = typeof lines === 'undefined' ? 0 : lines;
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_DOWN_PREFIX).plus0(FlatString.construct5("").plus0(FlatInt.toString((lines)).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_DOWN_SUFFIX).plus0(FlatString.construct5(""))))))));
	}

	static cursorMoveRight(columns) {
		columns = typeof columns === 'undefined' ? 0 : columns;
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_RIGHT_PREFIX).plus0(FlatString.construct5("").plus0(FlatInt.toString((columns)).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_RIGHT_SUFFIX).plus0(FlatString.construct5(""))))))));
	}

	static cursorMoveLeft(columns) {
		columns = typeof columns === 'undefined' ? 0 : columns;
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_LEFT_PREFIX).plus0(FlatString.construct5("").plus0(FlatInt.toString((columns)).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_LEFT_SUFFIX).plus0(FlatString.construct5(""))))))));
	}

	static cursorMoveDownAtBeginningOfLine(lines) {
		lines = typeof lines === 'undefined' ? 0 : lines;
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_DOWN_AT_BEGINNING_OF_LINE_PREFIX).plus0(FlatString.construct5("").plus0(FlatInt.toString((lines)).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_DOWN_AT_BEGINNING_OF_LINE_SUFFIX).plus0(FlatString.construct5(""))))))));
	}

	static cursorMoveUpAtBeginningOfLine(lines) {
		lines = typeof lines === 'undefined' ? 0 : lines;
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_UP_AT_BEGINNING_OF_LINE_PREFIX).plus0(FlatString.construct5("").plus0(FlatInt.toString((lines)).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_UP_AT_BEGINNING_OF_LINE_SUFFIX).plus0(FlatString.construct5(""))))))));
	}

	static cursorMoveToColumn(column) {
		column = typeof column === 'undefined' ? 0 : column;
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_TO_COLUMN_PREFIX).plus0(FlatString.construct5("").plus0(FlatInt.toString((column)).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_TO_COLUMN_SUFFIX).plus0(FlatString.construct5(""))))))));
	}

	static cursorRequestPosition() {
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_REQUEST_POSITION).plus0(FlatString.construct5(""))));
	}

	static cursorMoveUpOneLine() {
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_MOVE_UP_ONE_LINE).plus0(FlatString.construct5(""))));
	}

	static cursorSavePosition() {
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_SAVE_POSITION).plus0(FlatString.construct5(""))));
	}

	static cursorRestorePosition() {
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.CURSOR_RESTORE_POSITION).plus0(FlatString.construct5(""))));
	}

	static eraseFromCursorToEndOfScreen() {
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.ERASE_FROM_CURSOR_TO_END_OF_SCREEN).plus0(FlatString.construct5(""))));
	}

	static eraseFromCursorToBeginningOfScreen() {
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.ERASE_FROM_CURSOR_TO_BEGINNING_OF_SCREEN).plus0(FlatString.construct5(""))));
	}

	static eraseEntireScreen() {
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.ERASE_ENTIRE_SCREEN).plus0(FlatString.construct5(""))));
	}

	static eraseSavedLines() {
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.ERASE_SAVED_LINES).plus0(FlatString.construct5(""))));
	}

	static eraseFromCursorToEndOfLine() {
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.ERASE_FROM_CURSOR_TO_END_OF_LINE).plus0(FlatString.construct5(""))));
	}

	static eraseFromCursorToBeginningOfLine() {
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.ERASE_FROM_CURSOR_TO_BEGINNING_OF_LINE).plus0(FlatString.construct5(""))));
	}

	static eraseEntireLine() {
		return (EscapeCode.ESCAPE).plus0(FlatString.construct5("").plus0((EscapeCode.ERASE_ENTIRE_LINE).plus0(FlatString.construct5(""))));
	}

	static visibleCount(s) {
		if (s.count <= EscapeCode.ESCAPE.count + 1) {
			return s.count;
		}
		let iterator = EscapeCodeIterator.construct(s);
		let count = s.count;
		while (iterator.accessor_hasNext()) {
			iterator.accessor_stepNext();
			count -= iterator.positionEnd - iterator.position;
		}
		return count;
	}

	static visibleSubstring(s, start, end) {
		start = typeof start === 'undefined' ? 0 : start;
		end = typeof end === 'undefined' ? EscapeCode.visibleCount(s) : end;
		if (s.count <= EscapeCode.ESCAPE.count + 1) {
			return s.substring(start, end);
		}
		let iterator = EscapeCodeIterator.construct(s);
		while (iterator.accessor_hasNext()) {
			iterator.accessor_stepNext();
			if (start >= iterator.position) {
				start += iterator.accessor_codeCount();
			}
			if (end >= iterator.position) {
				end += iterator.accessor_codeCount();
			} else {
				break;
			}
		}
		return s.substring(start, end);
	}

	static wrapLine(value, lineLength, indentation, breakWord) {
		indentation = typeof indentation === 'undefined' ? FlatString.construct5("") : indentation;
		breakWord = typeof breakWord === 'undefined' ? false : breakWord;
		if (value.count <= lineLength) {
			return EscapeCode.generated277(value);
		}
		let vCount = EscapeCode.visibleCount(value);
		if (vCount <= lineLength) {
			return EscapeCode.generated278(value);
		}
		let offset = breakWord ? 0 : EscapeCode.findBreakWordOffset(value, vCount, lineLength);
		let line = EscapeCode.visibleSubstring(value, 0, lineLength - offset);
		let remainder = indentation.plus0(EscapeCode.visibleSubstring(value, lineLength - offset, undefined).trim(undefined, undefined, undefined));
		let lines = EscapeCode.generated279(line);
		return lines.__chain('addAll', [EscapeCode.wrapLine(remainder, lineLength, indentation, undefined)]);
	}

	static findBreakWordOffset(value, vCount, lineLength) {
		if (vCount <= lineLength) {
			return 0;
		}
		let nextChar = EscapeCode.visibleSubstring(value, lineLength, lineLength + 1).get(0);
		switch (nextChar) {
			case ' ':
			case '\n':
			case '\t':
			case '\r':
			return 0;
		}
		{
			let offset = 0;
			for (; offset < lineLength; offset++) {
				let endChar = EscapeCode.visibleSubstring(value, lineLength - 1 - offset, lineLength - offset).get(0);
				switch (endChar) {
					case ' ':
					case '\n':
					case '\t':
					case '\r':
					return offset;
				}
			}
		}
		return 0;
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated86(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44, value45, value46, value47, value48, value49, value50, value51, value52, value53, value54, value55, value56, value57, value58, value59, value60, value61, value62, value63, value64, value65, value66, value67, value68, value69, value70, value71, value72, value73, value74) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		temp[41] = value41;
		temp[42] = value42;
		temp[43] = value43;
		temp[44] = value44;
		temp[45] = value45;
		temp[46] = value46;
		temp[47] = value47;
		temp[48] = value48;
		temp[49] = value49;
		temp[50] = value50;
		temp[51] = value51;
		temp[52] = value52;
		temp[53] = value53;
		temp[54] = value54;
		temp[55] = value55;
		temp[56] = value56;
		temp[57] = value57;
		temp[58] = value58;
		temp[59] = value59;
		temp[60] = value60;
		temp[61] = value61;
		temp[62] = value62;
		temp[63] = value63;
		temp[64] = value64;
		temp[65] = value65;
		temp[66] = value66;
		temp[67] = value67;
		temp[68] = value68;
		temp[69] = value69;
		temp[70] = value70;
		temp[71] = value71;
		temp[72] = value72;
		temp[73] = value73;
		temp[74] = value74;
		return FlatArray.construct1(temp, 75);
	}

	static generated199(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		return FlatArray.construct1(temp, 26);
	}

	static generated277(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated278(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated279(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof EscapeCode.__lazy_accessor__js_class !== 'undefined' ? EscapeCode.__lazy_accessor__js_class : (EscapeCode.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/console/EscapeCode"), false, FlatObject.accessor__js_class(), FlatArray.construct(), EscapeCode.generated86(Field.construct(FlatString.construct5("ESCAPE")), Field.construct(FlatString.construct5("MODE_SUFFIX")), Field.construct(FlatString.construct5("RESET")), Field.construct(FlatString.construct5("BRIGHT")), Field.construct(FlatString.construct5("DIM")), Field.construct(FlatString.construct5("UNDERSCORE")), Field.construct(FlatString.construct5("BLINK")), Field.construct(FlatString.construct5("REVERSE")), Field.construct(FlatString.construct5("HIDDEN")), Field.construct(FlatString.construct5("BLACK_FOREGROUND")), Field.construct(FlatString.construct5("RED_FOREGROUND")), Field.construct(FlatString.construct5("GREEN_FOREGROUND")), Field.construct(FlatString.construct5("YELLOW_FOREGROUND")), Field.construct(FlatString.construct5("BLUE_FOREGROUND")), Field.construct(FlatString.construct5("MAGENTA_FOREGROUND")), Field.construct(FlatString.construct5("CYAN_FOREGROUND")), Field.construct(FlatString.construct5("WHITE_FOREGROUND")), Field.construct(FlatString.construct5("RGB_FOREGROUND_PREFIX")), Field.construct(FlatString.construct5("RGB_FOREGROUND_256_PREFIX")), Field.construct(FlatString.construct5("GRAY_FOREGROUND")), Field.construct(FlatString.construct5("BRIGHT_BLACK_FOREGROUND")), Field.construct(FlatString.construct5("BRIGHT_RED_FOREGROUND")), Field.construct(FlatString.construct5("BRIGHT_GREEN_FOREGROUND")), Field.construct(FlatString.construct5("BRIGHT_YELLOW_FOREGROUND")), Field.construct(FlatString.construct5("BRIGHT_BLUE_FOREGROUND")), Field.construct(FlatString.construct5("BRIGHT_MAGENTA_FOREGROUND")), Field.construct(FlatString.construct5("BRIGHT_CYAN_FOREGROUND")), Field.construct(FlatString.construct5("BRIGHT_WHITE_FOREGROUND")), Field.construct(FlatString.construct5("BLACK_BACKGROUND")), Field.construct(FlatString.construct5("RED_BACKGROUND")), Field.construct(FlatString.construct5("GREEN_BACKGROUND")), Field.construct(FlatString.construct5("YELLOW_BACKGROUND")), Field.construct(FlatString.construct5("BLUE_BACKGROUND")), Field.construct(FlatString.construct5("MAGENTA_BACKGROUND")), Field.construct(FlatString.construct5("CYAN_BACKGROUND")), Field.construct(FlatString.construct5("WHITE_BACKGROUND")), Field.construct(FlatString.construct5("RGB_BACKGROUND_PREFIX")), Field.construct(FlatString.construct5("RGB_BACKGROUND_256_PREFIX")), Field.construct(FlatString.construct5("GRAY_BACKGROUND")), Field.construct(FlatString.construct5("BRIGHT_BLACK_BACKGROUND")), Field.construct(FlatString.construct5("BRIGHT_RED_BACKGROUND")), Field.construct(FlatString.construct5("BRIGHT_GREEN_BACKGROUND")), Field.construct(FlatString.construct5("BRIGHT_YELLOW_BACKGROUND")), Field.construct(FlatString.construct5("BRIGHT_BLUE_BACKGROUND")), Field.construct(FlatString.construct5("BRIGHT_MAGENTA_BACKGROUND")), Field.construct(FlatString.construct5("BRIGHT_CYAN_BACKGROUND")), Field.construct(FlatString.construct5("BRIGHT_WHITE_BACKGROUND")), Field.construct(FlatString.construct5("CURSOR_MOVE_TO_HOME")), Field.construct(FlatString.construct5("CURSOR_MOVE_TO_POSITION_PREFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_TO_POSITION_SUFFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_UP_PREFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_UP_SUFFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_DOWN_PREFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_DOWN_SUFFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_RIGHT_PREFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_RIGHT_SUFFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_LEFT_PREFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_LEFT_SUFFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_DOWN_AT_BEGINNING_OF_LINE_PREFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_DOWN_AT_BEGINNING_OF_LINE_SUFFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_UP_AT_BEGINNING_OF_LINE_PREFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_UP_AT_BEGINNING_OF_LINE_SUFFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_TO_COLUMN_PREFIX")), Field.construct(FlatString.construct5("CURSOR_MOVE_TO_COLUMN_SUFFIX")), Field.construct(FlatString.construct5("CURSOR_REQUEST_POSITION")), Field.construct(FlatString.construct5("CURSOR_MOVE_UP_ONE_LINE")), Field.construct(FlatString.construct5("CURSOR_SAVE_POSITION")), Field.construct(FlatString.construct5("CURSOR_RESTORE_POSITION")), Field.construct(FlatString.construct5("ERASE_FROM_CURSOR_TO_END_OF_SCREEN")), Field.construct(FlatString.construct5("ERASE_FROM_CURSOR_TO_BEGINNING_OF_SCREEN")), Field.construct(FlatString.construct5("ERASE_ENTIRE_SCREEN")), Field.construct(FlatString.construct5("ERASE_SAVED_LINES")), Field.construct(FlatString.construct5("ERASE_FROM_CURSOR_TO_END_OF_LINE")), Field.construct(FlatString.construct5("ERASE_FROM_CURSOR_TO_BEGINNING_OF_LINE")), Field.construct(FlatString.construct5("ERASE_ENTIRE_LINE"))), EscapeCode.generated199(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("cursorMoveHome")), Function.construct(FlatString.construct5("cursorMoveToPosition")), Function.construct(FlatString.construct5("cursorMoveUp")), Function.construct(FlatString.construct5("cursorMoveDown")), Function.construct(FlatString.construct5("cursorMoveRight")), Function.construct(FlatString.construct5("cursorMoveLeft")), Function.construct(FlatString.construct5("cursorMoveDownAtBeginningOfLine")), Function.construct(FlatString.construct5("cursorMoveUpAtBeginningOfLine")), Function.construct(FlatString.construct5("cursorMoveToColumn")), Function.construct(FlatString.construct5("cursorRequestPosition")), Function.construct(FlatString.construct5("cursorMoveUpOneLine")), Function.construct(FlatString.construct5("cursorSavePosition")), Function.construct(FlatString.construct5("cursorRestorePosition")), Function.construct(FlatString.construct5("eraseFromCursorToEndOfScreen")), Function.construct(FlatString.construct5("eraseFromCursorToBeginningOfScreen")), Function.construct(FlatString.construct5("eraseEntireScreen")), Function.construct(FlatString.construct5("eraseSavedLines")), Function.construct(FlatString.construct5("eraseFromCursorToEndOfLine")), Function.construct(FlatString.construct5("eraseFromCursorToBeginningOfLine")), Function.construct(FlatString.construct5("eraseEntireLine")), Function.construct(FlatString.construct5("visibleCount")), Function.construct(FlatString.construct5("visibleSubstring")), Function.construct(FlatString.construct5("wrapLine")), Function.construct(FlatString.construct5("findBreakWordOffset")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class EscapeCodeIterator extends FlatObject {
	checkedNext = false;
	started = false;
	prevPosition = 0;
	prevPositionEnd = 0;
	nextPosition = 0;
	nextPositionEnd = 0;
	string = flat_null;
	position = 0;
	positionEnd = 0;

	static construct(string) {
		let __value = new EscapeCodeIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		EscapeCodeIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = EscapeCodeIterator.__init.call(__value, string);

		return __value;
	}

	destroy() {
	}

	static __init(string) {
		let self = this;

		this.string = string;
		return self;
	}

	findEscapeCodeEnd(index) {
		let self = this;

		let flat_local_0 = flat_null;
		let flat_local_1 = flat_null;
		let _js_default = -1;
		let exactCodeEnd = ((flat_local_0 = (EscapeCodeIterator.accessor_EXACT_ESCAPE_CODES().__callExtension(StreamListExtensions.List_stream, []).map((_x) => {
							return FlatInt.construct(self.findExactEscapeCodeEnd(_x, index));
					}).filter((_x) => {
							let flat_local_2 = flat_null;
							return ((flat_local_2 = (_x)) !== flat_null ? (flat_local_2.value) : 0) > index;
					}).firstOr(FlatInt.construct(_js_default)))) !== flat_null ? (flat_local_0.value) : 0);
		if (exactCodeEnd !== -1) {
			return exactCodeEnd;
		}
		return ((flat_local_1 = (EscapeCodeIterator.accessor_VARIABLE_ESCAPE_CODES().__callExtension(StreamListExtensions.List_stream, []).map((_x) => {
							return FlatInt.construct(self.findVariableEscapeCodeEnd(_x.get(0), _x.get(1), index));
					}).filter((_x) => {
							let flat_local_3 = flat_null;
							return ((flat_local_3 = (_x)) !== flat_null ? (flat_local_3.value) : 0) > index;
					}).firstOr(FlatInt.construct(_js_default)))) !== flat_null ? (flat_local_1.value) : 0);
	}

	findExactEscapeCodeEnd(escapeCode, index) {
		let start = index + EscapeCode.ESCAPE.count;
		if (this.string.count < start + escapeCode.count) {
			return -1;
		}
		{
			let i = 0;
			for (; i < escapeCode.count; i++) {
				if (this.string.get(start + i) !== escapeCode.get(i)) {
					break;
				}
				if (i === escapeCode.count - 1) {
					return start + i + 1;
				}
			}
		}
		return -1;
	}

	findVariableEscapeCodeEnd(prefix, suffix, index) {
		let start = index + EscapeCode.ESCAPE.count;
		if (this.string.count < start + prefix.count) {
			return -1;
		}
		{
			let i = 0;
			for (; i < prefix.count; i++) {
				if (this.string.get(start + i) !== prefix.get(i)) {
					return -1;
				}
			}
		}
		let variableEndIndex = start + prefix.count - 1;
		while (variableEndIndex < this.string.count - 1) {
			let c = this.string.get(++variableEndIndex);
			switch (c) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case ';':
				continue;
			}
			break;
		}
		{
			let i = 0;
			for (; i < suffix.count; i++) {
				if (variableEndIndex + i >= this.string.count) {
					return -1;
				}
				if (this.string.get(variableEndIndex + i) !== suffix.get(i)) {
					return -1;
				}
			}
		}
		return variableEndIndex + suffix.count;
	}

	reset() {
		this.checkedNext = false;
		this.started = false;
		this.prevPosition = -1;
		this.prevPositionEnd = -1;
		this.nextPosition = -1;
		this.nextPositionEnd = -1;
		this.position = -1;
		this.positionEnd = -1;
		return this;
	}

	static generated5(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated37(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		return FlatArray.construct1(temp, 14);
	}

	static generated141(value0, value1, value2, value3, value4, value5) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		return FlatArray.construct1(temp, 6);
	}

	static generated247(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44, value45) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		temp[41] = value41;
		temp[42] = value42;
		temp[43] = value43;
		temp[44] = value44;
		temp[45] = value45;
		return FlatArray.construct1(temp, 46);
	}

	static generated248(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated249(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated250(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated251(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated252(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated253(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated254(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated255(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated256(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated257(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated258(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated259(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated260(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		return FlatArray.construct1(temp, 12);
	}

	accessor_hasNext() {
		if (this.checkedNext) {
			return this.nextPosition !== -1;
		}
		let start = this.started ? this.positionEnd : 0;
		this.checkedNext = true;
		this.started = true;
		while (start < this.string.count - EscapeCode.ESCAPE.count - 1) {
			this.nextPosition = this.string.indexOf2(EscapeCode.ESCAPE, start, undefined, undefined);
			if (this.nextPosition === -1) {
				return false;
			}
			this.nextPositionEnd = this.findEscapeCodeEnd(this.nextPosition);
			if (this.nextPositionEnd !== -1) {
				return true;
			}
			start = this.nextPosition + EscapeCode.ESCAPE.count;
		}
		this.nextPosition = -1;
		this.nextPositionEnd = -1;
		return false;
	}

	accessor_stepNext() {
		if (this.accessor_hasNext()) {
			this.prevPosition = this.position;
			this.prevPositionEnd = this.positionEnd;
			this.position = this.nextPosition;
			this.positionEnd = this.nextPositionEnd;
			this.nextPosition = -1;
			this.nextPositionEnd = -1;
			this.checkedNext = false;
			return FlatInt.construct(this.position);
		}
		throw NoSuchElementException.construct(undefined);
	}

	static accessor__js_class() {
		return typeof EscapeCodeIterator.__lazy_accessor__js_class !== 'undefined' ? EscapeCodeIterator.__lazy_accessor__js_class : (EscapeCodeIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/console/EscapeCodeIterator"), false, FlatObject.accessor__js_class(), EscapeCodeIterator.generated5(Iterator.accessor__js_class()), EscapeCodeIterator.generated37(Field.construct(FlatString.construct5("position")), Field.construct(FlatString.construct5("positionEnd")), Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("hasPrevious")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("current")), Field.construct(FlatString.construct5("previous")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("code")), Field.construct(FlatString.construct5("previousCode")), Field.construct(FlatString.construct5("nextCode")), Field.construct(FlatString.construct5("codeCount")), Field.construct(FlatString.construct5("previousCodeCount")), Field.construct(FlatString.construct5("nextCodeCount"))), EscapeCodeIterator.generated141(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("findEscapeCodeEnd")), Function.construct(FlatString.construct5("findExactEscapeCodeEnd")), Function.construct(FlatString.construct5("findVariableEscapeCodeEnd")), Function.construct(FlatString.construct5("reset"))), this);
			})());
	}

	accessor_hasPrevious() {
		return this.prevPosition !== -1;
	}

	accessor_current() {
		return FlatInt.construct(this.position);
	}

	accessor_previous() {
		return FlatInt.construct(this.prevPosition);
	}

	accessor_next() {
		return FlatInt.construct(this.accessor_hasNext() ? this.nextPosition : -1);
	}

	accessor_code() {
		return this.position !== -1 ? this.string.substring(this.position, this.positionEnd) : flat_null;
	}

	accessor_previousCode() {
		return this.prevPosition !== -1 ? this.string.substring(this.prevPosition, this.prevPositionEnd) : flat_null;
	}

	accessor_nextCode() {
		return this.accessor_hasNext() ? this.string.substring(this.nextPosition, this.nextPositionEnd) : flat_null;
	}

	accessor_codeCount() {
		return this.position !== -1 ? this.positionEnd - this.position : -1;
	}

	accessor_previousCodeCount() {
		return this.prevPosition !== -1 ? this.prevPositionEnd - this.prevPosition : -1;
	}

	accessor_nextCodeCount() {
		return this.accessor_hasNext() ? this.nextPositionEnd - this.positionEnd : -1;
	}

	static accessor_EXACT_ESCAPE_CODES() {
		return typeof EscapeCodeIterator.__lazy_accessor_EXACT_ESCAPE_CODES !== 'undefined' ? EscapeCodeIterator.__lazy_accessor_EXACT_ESCAPE_CODES : (EscapeCodeIterator.__lazy_accessor_EXACT_ESCAPE_CODES = (() => {
					return EscapeCodeIterator.generated247(EscapeCode.RESET, EscapeCode.BRIGHT, EscapeCode.DIM, EscapeCode.UNDERSCORE, EscapeCode.BLINK, EscapeCode.REVERSE, EscapeCode.HIDDEN, EscapeCode.BLACK_FOREGROUND, EscapeCode.RED_FOREGROUND, EscapeCode.GREEN_FOREGROUND, EscapeCode.YELLOW_FOREGROUND, EscapeCode.BLUE_FOREGROUND, EscapeCode.MAGENTA_FOREGROUND, EscapeCode.CYAN_FOREGROUND, EscapeCode.WHITE_FOREGROUND, EscapeCode.GRAY_FOREGROUND, EscapeCode.BRIGHT_BLACK_FOREGROUND, EscapeCode.BRIGHT_RED_FOREGROUND, EscapeCode.BRIGHT_GREEN_FOREGROUND, EscapeCode.BRIGHT_YELLOW_FOREGROUND, EscapeCode.BRIGHT_BLUE_FOREGROUND, EscapeCode.BRIGHT_MAGENTA_FOREGROUND, EscapeCode.BRIGHT_CYAN_FOREGROUND, EscapeCode.BRIGHT_WHITE_FOREGROUND, EscapeCode.BLACK_BACKGROUND, EscapeCode.RED_BACKGROUND, EscapeCode.GREEN_BACKGROUND, EscapeCode.YELLOW_BACKGROUND, EscapeCode.BLUE_BACKGROUND, EscapeCode.MAGENTA_BACKGROUND, EscapeCode.CYAN_BACKGROUND, EscapeCode.WHITE_BACKGROUND, EscapeCode.GRAY_BACKGROUND, EscapeCode.BRIGHT_BLACK_BACKGROUND, EscapeCode.BRIGHT_RED_BACKGROUND, EscapeCode.BRIGHT_GREEN_BACKGROUND, EscapeCode.BRIGHT_YELLOW_BACKGROUND, EscapeCode.BRIGHT_BLUE_BACKGROUND, EscapeCode.BRIGHT_MAGENTA_BACKGROUND, EscapeCode.BRIGHT_CYAN_BACKGROUND, EscapeCode.BRIGHT_WHITE_BACKGROUND, EscapeCode.CURSOR_MOVE_TO_HOME, EscapeCode.CURSOR_REQUEST_POSITION, EscapeCode.CURSOR_MOVE_UP_ONE_LINE, EscapeCode.CURSOR_SAVE_POSITION, EscapeCode.CURSOR_RESTORE_POSITION);
			})());
	}

	static accessor_VARIABLE_ESCAPE_CODES() {
		return typeof EscapeCodeIterator.__lazy_accessor_VARIABLE_ESCAPE_CODES !== 'undefined' ? EscapeCodeIterator.__lazy_accessor_VARIABLE_ESCAPE_CODES : (EscapeCodeIterator.__lazy_accessor_VARIABLE_ESCAPE_CODES = (() => {
					return EscapeCodeIterator.generated260(FlatArray.construct().__chain('addAll', [EscapeCodeIterator.generated248(EscapeCode.RGB_FOREGROUND_PREFIX, EscapeCode.MODE_SUFFIX)]), FlatArray.construct().__chain('addAll', [EscapeCodeIterator.generated249(EscapeCode.RGB_FOREGROUND_256_PREFIX, EscapeCode.MODE_SUFFIX)]), FlatArray.construct().__chain('addAll', [EscapeCodeIterator.generated250(EscapeCode.RGB_BACKGROUND_PREFIX, EscapeCode.MODE_SUFFIX)]), FlatArray.construct().__chain('addAll', [EscapeCodeIterator.generated251(EscapeCode.RGB_BACKGROUND_256_PREFIX, EscapeCode.MODE_SUFFIX)]), FlatArray.construct().__chain('addAll', [EscapeCodeIterator.generated252(EscapeCode.CURSOR_MOVE_TO_POSITION_PREFIX, EscapeCode.CURSOR_MOVE_TO_POSITION_SUFFIX)]), FlatArray.construct().__chain('addAll', [EscapeCodeIterator.generated253(EscapeCode.CURSOR_MOVE_UP_PREFIX, EscapeCode.CURSOR_MOVE_UP_SUFFIX)]), FlatArray.construct().__chain('addAll', [EscapeCodeIterator.generated254(EscapeCode.CURSOR_MOVE_DOWN_PREFIX, EscapeCode.CURSOR_MOVE_DOWN_SUFFIX)]), FlatArray.construct().__chain('addAll', [EscapeCodeIterator.generated255(EscapeCode.CURSOR_MOVE_RIGHT_PREFIX, EscapeCode.CURSOR_MOVE_RIGHT_SUFFIX)]), FlatArray.construct().__chain('addAll', [EscapeCodeIterator.generated256(EscapeCode.CURSOR_MOVE_LEFT_PREFIX, EscapeCode.CURSOR_MOVE_LEFT_SUFFIX)]), FlatArray.construct().__chain('addAll', [EscapeCodeIterator.generated257(EscapeCode.CURSOR_MOVE_DOWN_AT_BEGINNING_OF_LINE_PREFIX, EscapeCode.CURSOR_MOVE_DOWN_AT_BEGINNING_OF_LINE_SUFFIX)]), FlatArray.construct().__chain('addAll', [EscapeCodeIterator.generated258(EscapeCode.CURSOR_MOVE_UP_AT_BEGINNING_OF_LINE_PREFIX, EscapeCode.CURSOR_MOVE_UP_AT_BEGINNING_OF_LINE_SUFFIX)]), FlatArray.construct().__chain('addAll', [EscapeCodeIterator.generated259(EscapeCode.CURSOR_MOVE_TO_COLUMN_PREFIX, EscapeCode.CURSOR_MOVE_TO_COLUMN_SUFFIX)]));
			})());
	}

	static __assignments() {
		this.checkedNext = false;
		this.started = false;
		this.prevPosition = -1;
		this.prevPositionEnd = -1;
		this.nextPosition = -1;
		this.nextPositionEnd = -1;
		this.position = -1;
		this.positionEnd = -1;
	}
}

class EventStream extends FlatObject {
	eventListeners = flat_null;
	eventBacklog = flat_null;
	pendingFutures = flat_null;
	backlogEvents = false;
	closed = false;
	backlogMissedEvents = false;
	static log = flat_null;

	static construct(backlogMissedEvents) {
		let __value = new EventStream();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		EventStream.__assignments.apply(__value, [].slice.call(arguments));
		__value = EventStream.__init.call(__value, backlogMissedEvents);

		return __value;
	}

	destroy() {
	}

	static __init(backlogMissedEvents) {
		let self = this;

		backlogMissedEvents = typeof backlogMissedEvents === 'undefined' ? false : backlogMissedEvents;
		this.backlogMissedEvents = backlogMissedEvents;
		return self;
	}

	emit(eventType, data) {
		let self = this;

		let listeners = flat_null;
		data = typeof data === 'undefined' ? flat_null : data;
		if (this.closed) {
			return flat_null;
		}
		if (this.backlogEvents) {
			this.eventBacklog.getOrDefault(eventType, () => {
					return FlatArray.construct();
			}).add0(data);
		}
		if ((listeners = this.eventListeners.get(eventType)) !== flat_null) {
			listeners.forEach1((_x, _i, _array) => {
					_x(data, this);
			});
		}
		return data;
	}

	on(eventType, action) {
		let self = this;

		let flat_local_0 = flat_null;
		if (this.closed) {
			return this;
		}
		let a = action;
		this.eventListeners.getOrDefault(eventType, () => {
				return FlatArray.construct();
		}).add0(a);
		if (this.backlogMissedEvents) {
			if ((flat_local_0 = this.eventBacklog.get(eventType)) !== flat_null) {
				flat_local_0.forEach1((_x, _i, _array) => {
						a(_x, this);
				});
			}
		}
		return this;
	}

	close() {
		let self = this;

		this.pendingFutures.forEach1((_x, _i, _array) => {
				_x.cancel();
		});
		this.closed = true;
	}

	async consumeAll(eventType, closeEvent) {
		let self = this;

		closeEvent = typeof closeEvent === 'undefined' ? FlatString.construct5("close") : closeEvent;
		if (this.closed) {
			return FlatArray.construct();
		}
		let results = FlatArray.construct();
		this.on(eventType, (_data, _stream) => {
				results.add0(_data);
		});
		(await this.waitFor(closeEvent).get());
		return results;
	}

	waitFor(eventType) {
		let self = this;

		let future = flat_null;
		future = Future.fromResolve(async (_resolve, _reject) => {
				let event = flat_null;
				let flat_local_0 = flat_null;
				if ((event = ((flat_local_0 = self.eventBacklog.get(eventType)) !== flat_null ? (flat_local_0.accessor_last()) : flat_null)) !== flat_null) {
					self.pendingFutures.remove1(future);
					_resolve(event);
				} else if (self.closed) {
					self.pendingFutures.remove1(future);
					_resolve(flat_null);
				} else {
					self.on(eventType, (_data, _stream) => {
							self.pendingFutures.remove1(future);
							_resolve(_data);
					});
				}
		});
		if (!future.completed) {
			this.pendingFutures.add0(future);
		}
		return future;
	}

	static generated15(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated42(value0, value1, value2, value3, value4, value5, value6) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		return FlatArray.construct1(temp, 7);
	}

	static accessor__js_class() {
		return typeof EventStream.__lazy_accessor__js_class !== 'undefined' ? EventStream.__lazy_accessor__js_class : (EventStream.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/eventstream/EventStream"), false, FlatObject.accessor__js_class(), FlatArray.construct(), EventStream.generated15(Field.construct(FlatString.construct5("backlogEvents")), Field.construct(FlatString.construct5("closed")), Field.construct(FlatString.construct5("backlogMissedEvents"))), EventStream.generated42(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("emit")), Function.construct(FlatString.construct5("on")), Function.construct(FlatString.construct5("close")), Function.construct(FlatString.construct5("consumeAll")), Function.construct(FlatString.construct5("waitFor"))), this);
			})());
	}

	static __assignments() {
		this.eventListeners = HashMap.construct0(undefined, undefined);
		this.eventBacklog = HashMap.construct0(undefined, undefined);
		this.pendingFutures = FlatArray.construct();
		this.backlogEvents = true;
		this.closed = false;
	}
}

class ExceptionData extends FlatObject {
	parent = flat_null;
	caught = flat_null;
	thrownException = flat_null;

	static construct() {
		let __value = new ExceptionData();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		ExceptionData.__assignments.apply(__value, [].slice.call(arguments));
		__value = ExceptionData.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	addCaught(type, soft) {
		soft = typeof soft === 'undefined' ? false : soft;
		this.caught.add0(CaughtException.construct(type, soft));
	}

	getDataByException(exception, soft) {
		let self = this;

		soft = typeof soft === 'undefined' ? false : soft;
		let data = this;
		while (true) {
			if (data.caught.any0((_x, _i, _list) => {
						return (!soft || _x.soft) && exception.accessor__js_class().isOfType(_x.type);
			})) {
				return data;
			} else if (!((data.parent) !== flat_null)) {
				return soft ? flat_null : data;
			}
			data = data.parent;
		}
	}

	toString() {
		return FlatString.construct5("{Thrown: ").plus0((this.thrownException).toString().plus0(FlatString.construct5(", Caught: ").plus0((this.caught).toString().plus0(FlatString.construct5("}")))));
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated30(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated85(value0, value1, value2, value3, value4) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		return FlatArray.construct1(temp, 5);
	}

	static accessor__js_class() {
		return typeof ExceptionData.__lazy_accessor__js_class !== 'undefined' ? ExceptionData.__lazy_accessor__js_class : (ExceptionData.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/exception/ExceptionData"), false, FlatObject.accessor__js_class(), FlatArray.construct(), ExceptionData.generated30(Field.construct(FlatString.construct5("parent")), Field.construct(FlatString.construct5("caught")), Field.construct(FlatString.construct5("thrownException"))), ExceptionData.generated85(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("addCaught")), Function.construct(FlatString.construct5("getDataByException")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class ExecutionResponse extends FlatObject {
	stdout = flat_null;
	stderr = flat_null;
	exitCode = 0;

	static construct(stdout, stderr, exitCode) {
		let __value = new ExecutionResponse();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		ExecutionResponse.__assignments.apply(__value, [].slice.call(arguments));
		__value = ExecutionResponse.__init.call(__value, stdout, stderr, exitCode);

		return __value;
	}

	destroy() {
	}

	static __init(stdout, stderr, exitCode) {
		let self = this;

		this.stdout = stdout;
		this.stderr = stderr;
		this.exitCode = exitCode;
		return self;
	}

	toString() {
		return FlatString.construct5("ExecutionResponse {\n    stdout: ").plus0((this.stdout).toString().plus0(FlatString.construct5(",\n    stderr: ").plus0((this.stderr).toString().plus0(FlatString.construct5(",\n    exitCode: ").plus0(FlatInt.toString((this.exitCode)).plus0(FlatString.construct5("\n}")))))));
	}

	static generated18(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated57(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static accessor__js_class() {
		return typeof ExecutionResponse.__lazy_accessor__js_class !== 'undefined' ? ExecutionResponse.__lazy_accessor__js_class : (ExecutionResponse.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/system/ExecutionResponse"), false, FlatObject.accessor__js_class(), FlatArray.construct(), ExecutionResponse.generated18(Field.construct(FlatString.construct5("stdout")), Field.construct(FlatString.construct5("stderr")), Field.construct(FlatString.construct5("exitCode"))), ExecutionResponse.generated57(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("toString"))), this);
			})());
	}

	static __assignments() {
	}
}

class FancyOutputStream extends FlatObject {
	contentWidth = 0;
	headerPattern = flat_null;
	out = flat_null;

	static construct(out, contentWidth, headerPattern) {
		let __value = new FancyOutputStream();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FancyOutputStream.__assignments.apply(__value, [].slice.call(arguments));
		__value = FancyOutputStream.__init.call(__value, out, contentWidth, headerPattern);

		return __value;
	}

	destroy() {
	}

	static __init(out, contentWidth, headerPattern) {
		let self = this;

		out = typeof out === 'undefined' ? FlatConsole.out : out;
		contentWidth = typeof contentWidth === 'undefined' ? 80 : contentWidth;
		headerPattern = typeof headerPattern === 'undefined' ? FlatString.construct5("=") : headerPattern;
		this.out = out;
		this.contentWidth = contentWidth;
		this.headerPattern = headerPattern;
		return self;
	}

	writeHeader0(message) {
		return this.writeHeader1(message.toString(), undefined, undefined, undefined);
	}

	writeHeader1(message, pattern, rightPattern, symmetrical) {
		pattern = typeof pattern === 'undefined' ? this.headerPattern : pattern;
		rightPattern = typeof rightPattern === 'undefined' ? pattern : rightPattern;
		symmetrical = typeof symmetrical === 'undefined' ? false : symmetrical;
		let fullHeaderCount = this.contentWidth - message.count - 2;
		let sideCount = ~~(fullHeaderCount / 2);
		if (symmetrical) {
			rightPattern = pattern.reverse(true);
		}
		let leftCount = ~~(sideCount / pattern.count);
		let rightCount = ~~(sideCount / rightPattern.count);
		let remainingLeft = ~~(sideCount % pattern.count);
		let remainingRight = (~~(sideCount % rightPattern.count)) + (~~(message.count % 2));
		this.write(pattern.repeat(leftCount)).write(pattern.substring(undefined, remainingLeft));
		this.write(FlatString.construct5(" ")).write(message).write(FlatString.construct5(" "));
		this.write(rightPattern.repeat(rightCount)).write(rightPattern.substring(undefined, remainingRight));
		this.writeLine(undefined);
		return this;
	}

	writeSeparator(pattern) {
		pattern = typeof pattern === 'undefined' ? this.headerPattern : pattern;
		this.writeLine(pattern.repeat(this.contentWidth));
		return this;
	}

	write(value) {
		value = typeof value === 'undefined' ? FlatString.construct5("") : value;
		this.out.write(value);
		return this;
	}

	writeLine(value) {
		value = typeof value === 'undefined' ? FlatString.construct5("") : value;
		this.out.writeLine(value);
		return this;
	}

	static generated25(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated87(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated100(value0, value1, value2, value3, value4, value5, value6) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		return FlatArray.construct1(temp, 7);
	}

	static accessor__js_class() {
		return typeof FancyOutputStream.__lazy_accessor__js_class !== 'undefined' ? FancyOutputStream.__lazy_accessor__js_class : (FancyOutputStream.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/FancyOutputStream"), false, FlatObject.accessor__js_class(), FancyOutputStream.generated25(OutputStream.accessor__js_class()), FancyOutputStream.generated87(Field.construct(FlatString.construct5("contentWidth")), Field.construct(FlatString.construct5("headerPattern")), Field.construct(FlatString.construct5("out"))), FancyOutputStream.generated100(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("writeHeader")), Function.construct(FlatString.construct5("writeHeader")), Function.construct(FlatString.construct5("writeSeparator")), Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("writeLine"))), this);
			})());
	}

	static __assignments() {
		this.contentWidth = 80;
		this.headerPattern = FlatString.construct5("=");
	}
}

class Field extends FlatObject {
	name = flat_null;

	static construct(name) {
		let __value = new Field();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Field.__assignments.apply(__value, [].slice.call(arguments));
		__value = Field.__init.call(__value, name);

		return __value;
	}

	static __init(name) {
		let self = this;

		this.name = name;
		return self;
	}

	getValue(obj) {
		return obj[this.name.chars.data];
	}

	setValue(obj, value) {
		return obj[this.name.chars.data] = value;
	}

	copy(name) {
		name = typeof name === 'undefined' ? this.name : name;
		return Field.construct(name);
	}

	equals(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(Field.accessor__js_class()) && this.equals0(other);
	}

	equals0(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && (this.name.equals(other.name));
	}

	toString() {
		return FlatString.construct5("Field {\n  \"name\": ").plus0((this.name !== flat_null && this.name.accessor__js_class().isOfType(FlatString.accessor__js_class()) ? Char.toString('"').plus0(this.name.toString().plus0(Char.toString('"'))) : this.name.toString()).plus0(FlatString.construct5("\n}")));
	}

	toJson(format, tab) {
		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		return format ? FlatString.construct5("{\n").plus0((tab).plus0(FlatString.construct5("\"name\":").plus0((format ? FlatString.construct5(" ") : FlatString.construct5("")).plus0(FlatString.construct5("").plus0((this.name.toJson(format, tab)).plus0(FlatString.construct5("\n}"))))))) : FlatString.construct5("{\"name\":").plus0((format ? FlatString.construct5(" ") : FlatString.construct5("")).plus0(FlatString.construct5("").plus0((this.name.toJson(format, tab)).plus0(FlatString.construct5("}")))));
	}

	toBuilder() {
		return flat_meta_Field_Builder.construct(this.name);
	}

	copyTo(target, name) {
		name = typeof name === 'undefined' ? this.name : name;
		return target.toBuilder().name(name).build();
	}

	static generated17(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated38(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		return FlatArray.construct1(temp, 11);
	}

	static accessor__js_class() {
		return typeof Field.__lazy_accessor__js_class !== 'undefined' ? Field.__lazy_accessor__js_class : (Field.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/meta/Field"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Field.generated17(Field.construct(FlatString.construct5("name"))), Field.generated38(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("getValue")), Function.construct(FlatString.construct5("setValue")), Function.construct(FlatString.construct5("copy")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("toBuilder")), Function.construct(FlatString.construct5("copyTo"))), this);
			})());
	}

	static __assignments() {
	}
	static flat_meta_Field_Builder
}

class flat_meta_Field_Builder extends FlatObject {
	name_value = flat_null;

	static construct(name) {
		let __value = new flat_meta_Field_Builder();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		flat_meta_Field_Builder.__assignments.apply(__value, [].slice.call(arguments));
		__value = flat_meta_Field_Builder.__init.call(__value, name);

		return __value;
	}

	name(value) {
		this.name_value = value;
		return this;
	}

	build() {
		return Field.construct(this.name_value);
	}

	static __init(name) {
		let self = this;

		name = typeof name === 'undefined' ? this.name_value : name;
		this.name_value = name;
		return self;
	}

	static generated179(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static accessor__js_class() {
		return typeof flat_meta_Field_Builder.__lazy_accessor__js_class !== 'undefined' ? flat_meta_Field_Builder.__lazy_accessor__js_class : (flat_meta_Field_Builder.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/meta/Field.Builder"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), flat_meta_Field_Builder.generated179(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("name")), Function.construct(FlatString.construct5("build")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class File extends FlatObject {
	location = flat_null;
	normalizedLocation = flat_null;
	static EXECUTED_FILE = flat_null;
	static EXECUTED_DIRECTORY = flat_null;

	static construct() {
		let __value = new File();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		File.__assignments.apply(__value, [].slice.call(arguments));
		__value = File.__init.call(__value);

		return __value;
	}

	static construct0(parent, path) {
		let __value = new File();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		File.__assignments.apply(__value, [].slice.call(arguments));
		__value = File.__init0.call(__value, parent, path);

		return __value;
	}

	static construct1(location) {
		let __value = new File();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		File.__assignments.apply(__value, [].slice.call(arguments));
		__value = File.__init1.call(__value, location);

		return __value;
	}

	destroy() {
	}

	getChildFiles(recursive, includeHidden, includeDirectories) {
		let self = this;

		recursive = typeof recursive === 'undefined' ? false : recursive;
		includeHidden = typeof includeHidden === 'undefined' ? false : includeHidden;
		includeDirectories = typeof includeDirectories === 'undefined' ? false : includeDirectories;
		let list = this.accessor_files();
		let directories = list.filter1((_x, _i, _array) => {
				return _x.accessor_isDirectory();
		}).filter1((_x, _i, _array) => {
				return includeHidden || !_x.accessor_isHidden();
		}).filter1((_x, _i, _array) => {
				return !_x.accessor_name().equals(FlatString.construct5("."));
		}).filter1((_x, _i, _array) => {
				return !_x.accessor_name().equals(FlatString.construct5(".."));
		});
		if (recursive) {
			directories.forEach1((_x, _i, _array) => {
					list.addAll(_x.getChildFiles(true, undefined, undefined));
			});
		}
		return list.filter1((_x, _i, _array) => {
				return includeDirectories || !_x.accessor_isDirectory();
		}).filter1((_x, _i, _array) => {
				return includeHidden || !_x.accessor_isHidden();
		});
	}

	normalizeLocation(location) {
		return location.replace(FlatString.construct5("\\"), FlatString.construct5("/"), undefined).trimEnd2('/', undefined);
	}

	equals(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(File.accessor__js_class()) && this.equals0(other);
	}

	equals0(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && other.normalizedLocation.equals(this.normalizedLocation);
	}

	toString() {
		return FlatString.construct5("{File \"").plus0((this.normalizedLocation).plus0(FlatString.construct5("\"}")));
	}

	static __init() {
		let self = this;

		return self;
	}

	static __init0(parent, path) {
		let self = this;

		self = File.__init1.call(this, parent.normalizedLocation.plus0(FlatString.construct5("/").plus0(path.trimStart2('/', undefined))));
		return self;
	}

	static __init1(location) {
		let self = this;

		this.location = location;
		this.normalizedLocation = this.normalizeLocation(location);
		return self;
	}

	toFileSize() {
		return FlatLong.toFileSize(this.accessor_bytes());
	}

	static getExecutedFile() {
		let data = flat_null;
		if (typeof __filename !== 'undefined') {
			data = __filename;
		} else {
			data = "";
		}
		return File.construct1(FlatString.construct4(data));
	}

	static getExecutedDirectory() {
		let data = flat_null;
		if (typeof __dirname !== 'undefined') {
			data = __dirname;
		} else {
			data = "";
		}
		return File.construct1(FlatString.construct4(data));
	}

	async getCanonicalFile() {
		return File.construct1((await this.getCanonicalPath()));
	}

	getCanonicalFileSync() {
		return File.construct1(this.getCanonicalPathSync());
	}

	async getCanonicalPath() {
		let current = this;
		let names = FlatArray.construct();
		while ((current) !== flat_null) {
			let target = current.accessor_isSymbolicLink() ? (await current.getSymbolicLinkTarget()) : current.normalizedLocation;
			if (!this.normalizedLocation.startsWith1(target)) {
				return this.normalizeLocation(target.plus0(FlatString.construct5("/").plus0(names.join(FlatString.construct5("/"), undefined, undefined, undefined))));
			}
			names.unshift(current.accessor_name());
			current = current.getParent();
		}
		return this.normalizedLocation;
	}

	getCanonicalPathSync() {
		let current = this;
		let names = FlatArray.construct();
		while ((current) !== flat_null) {
			let target = current.accessor_isSymbolicLink() ? current.getSymbolicLinkTargetSync() : current.normalizedLocation;
			if (!this.normalizedLocation.startsWith1(target)) {
				return this.normalizeLocation(target.plus0(FlatString.construct5("/").plus0(names.join(FlatString.construct5("/"), undefined, undefined, undefined))));
			}
			names.unshift(current.accessor_name());
			current = current.getParent();
		}
		return this.normalizedLocation;
	}

	async getSymbolicLinkTarget() {
		let data = flat_null;
		data = await new Promise((resolve, reject) => {
				File_FileGlobal.accessor_fs().readlink(this.location.chars.data, (err, target) => {
						if (err) {
							reject(err);
						} else {
							resolve(target);
						}
				});
		});
		return this.normalizeLocation(FlatString.construct4(data));
	}

	getSymbolicLinkTargetSync() {
		let data = flat_null;
		data = File_FileGlobal.accessor_fs().readlinkSync(this.location.chars.data);
		return this.normalizeLocation(FlatString.construct4(data));
	}

	getParent() {
		let data = flat_null;
		data = File_FileGlobal.accessor_path().resolve(this.location.chars.data, '..');
		let parentLocation = FlatString.construct4(data);
		if (!parentLocation.equals(this.location)) {
			return File.construct1(parentLocation);
		}
		return flat_null;
	}

	getChild(location) {
		return File.construct1(this.location.plus0(FlatString.construct5("/").plus0(location)));
	}

	async delete(recursive, force) {
		let errorMessage = flat_null;
		recursive = typeof recursive === 'undefined' ? false : recursive;
		force = typeof force === 'undefined' ? false : force;
		if (!this.accessor_exists()) {
			return false;
		}
		errorMessage = await new Promise(resolve => {
				File_FileGlobal.accessor_fs().rm(this.location.chars.data, {
						recursive: recursive,
						force: force
					}, (err) => {
						if (err) {
							resolve(err.message);
						} else {
							resolve(null);
						}
				});
		});
		if (errorMessage) {
			throw Exception.construct(FlatString.construct4(errorMessage));
		}
		return true;
	}

	deleteSync(recursive, force) {
		let errorMessage = flat_null;
		recursive = typeof recursive === 'undefined' ? false : recursive;
		force = typeof force === 'undefined' ? false : force;
		if (!this.accessor_exists()) {
			return false;
		}
		errorMessage = File_FileGlobal.accessor_fs().rmSync(this.location.chars.data, {
				recursive: recursive,
				force: force
			}, (err) => {
				if (err) {
					resolve(err.message);
				} else {
					resolve(null);
				}
		});
		if (errorMessage) {
			throw Exception.construct(FlatString.construct4(errorMessage));
		}
		return true;
	}

	async mkdirs() {
		return (await this.mkdir(true, undefined));
	}

	async mkdir(recursive, failOnExistsAlready) {
		recursive = typeof recursive === 'undefined' ? false : recursive;
		failOnExistsAlready = typeof failOnExistsAlready === 'undefined' ? false : failOnExistsAlready;
		if (failOnExistsAlready || !this.accessor_exists()) {
			await File_FileGlobal.accessor_fs().promises.mkdir(this.location.chars.data, {recursive: recursive});
		}
		return true;
	}

	async copy(destination) {
		if (this.accessor_isDirectory()) {
			let flat_local_0 = flat_null;
			if (!destination.accessor_isDirectory()) {
				(await destination.mkdir(undefined, undefined));
			}
			let files = this.getChildFiles(false, true, true);
			flat_local_0 = (files).accessor_iterator();
			while (flat_local_0.accessor_hasNext()) {
				let file = flat_null;
				file = flat_local_0.accessor_stepNext();
				(await file.copy(File.construct0(destination, file.accessor_name())));
			}
		} else {
			await File_FileGlobal.accessor_fs().promises.copyFile(this.location.chars.data, destination.location.chars.data);
		}
		return true;
	}

	async chmod(mode) {
		await File_FileGlobal.accessor_fs().promises.chmod(this.location.chars.data, mode.chars.data);
		return true;
	}

	async symbolicLinkTo(target) {
		await new Promise((resolve, reject) => {
				File_FileGlobal.accessor_fs().symlink(target.location.chars.data, this.location.chars.data, 'junction', (err) => {
						if (err) {
							reject(err);
						} else {
							resolve();
						}
				});
		});
		return true;
	}

	static async symbolicLink(from, to) {
		return (await from.symbolicLinkTo(to));
	}

	createReadStream(encoding) {
		let data = flat_null;
		encoding = typeof encoding === 'undefined' ? FlatString.construct5("utf8") : encoding;
		let stream = EventStream.construct(true);
		const readableStream = File_FileGlobal.accessor_fs().createReadStream(
			this.location.chars.data,
			{
				encoding: encoding.chars.data
			}
		);
		readableStream.on('error', (error) => {
				if (typeof error === 'string') {
					data = error;
				} else {
					data = error + "";
				}
				stream.emit(FlatString.construct5("error"), FlatString.construct4(data));
		});
		readableStream.on('data', (chunk) => {
				data = chunk;
				stream.emit(FlatString.construct5("data"), FlatString.construct4(data));
		});
		readableStream.on('close', () => {
				stream.emit(FlatString.construct5("close"), undefined);
		});
		return stream;
	}

	static generated46(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		return FlatArray.construct1(temp, 19);
	}

	static generated158(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		return FlatArray.construct1(temp, 31);
	}

	accessor_nativeLocation() {
		if (System.OS_INT === System.WINDOWS) {
			return this.normalizedLocation.replace(FlatString.construct5("/"), FlatString.construct5("\\"), undefined);
		} else {
			return this.normalizedLocation;
		}

	}

	accessor_isDirectory() {
		if (!this.accessor_exists()) {
			return false;
		}
		if (File_FileGlobal.accessor_fs().lstatSync(this.location.chars.data).isDirectory()) {
			return true;
		}
		return false;
	}

	accessor_isSymbolicLink() {
		if (!this.accessor_exists()) {
			return false;
		}
		if (File_FileGlobal.accessor_fs().lstatSync(this.location.chars.data).isSymbolicLink()) {
			return true;
		}
		return false;
	}

	accessor_exists() {
		if (File_FileGlobal.accessor_fs().existsSync(this.location.chars.data)) {
			return true;
		}
		return false;
	}

	accessor_files() {
		let filename = flat_null;
		if (!this.accessor_isDirectory()) {
			throw Exception.construct(FlatString.construct5("\"").plus0((this.location).plus0(FlatString.construct5("\" must be a directory to list files"))));
		}
		let list = FlatArray.construct();
		File_FileGlobal.accessor_fs().readdirSync(this.location.chars.data).forEach((f) => {
				filename = f;
				list.add0(File.construct1((this.location).plus0(FlatString.construct5("/").plus0((FlatString.construct4(filename)).plus0(FlatString.construct5(""))))));
		});
		return list;
	}

	accessor_bytes() {
		return File_FileGlobal.accessor_fs().statSync(this.location.chars.data).size;
	}

	static accessor__js_class() {
		return typeof File.__lazy_accessor__js_class !== 'undefined' ? File.__lazy_accessor__js_class : (File.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/File"), false, FlatObject.accessor__js_class(), FlatArray.construct(), File.generated46(Field.construct(FlatString.construct5("location")), Field.construct(FlatString.construct5("normalizedLocation")), Field.construct(FlatString.construct5("name")), Field.construct(FlatString.construct5("extension")), Field.construct(FlatString.construct5("fullExtension")), Field.construct(FlatString.construct5("extensionName")), Field.construct(FlatString.construct5("rootName")), Field.construct(FlatString.construct5("extensionNames")), Field.construct(FlatString.construct5("extensions")), Field.construct(FlatString.construct5("isHidden")), Field.construct(FlatString.construct5("isFile")), Field.construct(FlatString.construct5("isDirectory")), Field.construct(FlatString.construct5("exists")), Field.construct(FlatString.construct5("nativeLocation")), Field.construct(FlatString.construct5("isSymbolicLink")), Field.construct(FlatString.construct5("files")), Field.construct(FlatString.construct5("bytes")), Field.construct(FlatString.construct5("EXECUTED_FILE")), Field.construct(FlatString.construct5("EXECUTED_DIRECTORY"))), File.generated158(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("getChildFiles")), Function.construct(FlatString.construct5("normalizeLocation")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("toFileSize")), Function.construct(FlatString.construct5("getExecutedFile")), Function.construct(FlatString.construct5("getExecutedDirectory")), Function.construct(FlatString.construct5("getCanonicalFile")), Function.construct(FlatString.construct5("getCanonicalFileSync")), Function.construct(FlatString.construct5("getCanonicalPath")), Function.construct(FlatString.construct5("getCanonicalPathSync")), Function.construct(FlatString.construct5("getSymbolicLinkTarget")), Function.construct(FlatString.construct5("getSymbolicLinkTargetSync")), Function.construct(FlatString.construct5("getParent")), Function.construct(FlatString.construct5("getChild")), Function.construct(FlatString.construct5("delete")), Function.construct(FlatString.construct5("deleteSync")), Function.construct(FlatString.construct5("mkdirs")), Function.construct(FlatString.construct5("mkdir")), Function.construct(FlatString.construct5("copy")), Function.construct(FlatString.construct5("chmod")), Function.construct(FlatString.construct5("symbolicLinkTo")), Function.construct(FlatString.construct5("symbolicLink")), Function.construct(FlatString.construct5("createReadStream"))), this);
			})());
	}

	accessor_name() {
		return this.normalizedLocation.substring(this.normalizedLocation.lastIndexOf0('/', undefined, undefined) + 1, undefined);
	}

	accessor_extension() {
		return this.normalizedLocation.substring(this.normalizedLocation.lastIndexOf0('.', undefined, this.normalizedLocation.count), undefined);
	}

	accessor_fullExtension() {
		return this.normalizedLocation.substring(this.normalizedLocation.indexOf0('.', this.normalizedLocation.lastIndexOf0('/', undefined, 0), undefined, this.normalizedLocation.count), undefined);
	}

	accessor_extensionName() {
		return this.normalizedLocation.substring(this.normalizedLocation.lastIndexOf0('.', undefined, this.normalizedLocation.count - 1) + 1, undefined);
	}

	accessor_rootName() {
		return this.accessor_name().substring(undefined, this.accessor_name().count - this.accessor_extension().count);
	}

	accessor_extensionNames() {
		return this.accessor_fullExtension().__callExtension(RegexStringExtensions.String_split, [Pattern.construct(FlatString.construct5("\\."))]).skip(1);
	}

	accessor_extensions() {
		let self = this;

		return this.accessor_extensionNames().map1((_x, _i, _array) => {
				return FlatString.construct5(".").plus0((_x).plus0(FlatString.construct5("")));
		});
	}

	accessor_isHidden() {
		return this.accessor_name().startsWith1(FlatString.construct5("."));
	}

	accessor_isFile() {
		return this.accessor_exists() && !this.accessor_isDirectory();
	}

	static __assignments() {
	}
	static File_FileGlobal
}

class File_FileGlobal extends FlatObject {
	static generated202(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor_fs() {
		return typeof File_FileGlobal.__lazy_accessor_fs !== 'undefined' ? File_FileGlobal.__lazy_accessor_fs : (File_FileGlobal.__lazy_accessor_fs = (() => {
					return require('fs');
			})());
	}

	static accessor_path() {
		return typeof File_FileGlobal.__lazy_accessor_path !== 'undefined' ? File_FileGlobal.__lazy_accessor_path : (File_FileGlobal.__lazy_accessor_path = (() => {
					return require('path');
			})());
	}

	static accessor__js_class() {
		return typeof File_FileGlobal.__lazy_accessor__js_class !== 'undefined' ? File_FileGlobal.__lazy_accessor__js_class : (File_FileGlobal.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/File.FileGlobal"), false, FlatObject.accessor__js_class(), FlatArray.construct(), File_FileGlobal.generated202(Field.construct(FlatString.construct5("fs")), Field.construct(FlatString.construct5("path"))), FlatArray.construct(), this);
			})());
	}

	static __assignments() {
	}

	static __assignments() {
	}
}

class FileNotFoundException extends Exception {
	static construct(file) {
		let __value = new FileNotFoundException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		FileNotFoundException.__assignments.apply(__value, [].slice.call(arguments));
		__value = FileNotFoundException.__init.call(__value, file);

		return __value;
	}

	destroy() {
	}

	static __init(file) {
		let self = this;

		self = Exception.__init.call(this, FlatString.construct5("File at location '").plus0((file.location).plus0(FlatString.construct5("' does not exist"))));
		return self;
	}

	static generated34(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof FileNotFoundException.__lazy_accessor__js_class !== 'undefined' ? FileNotFoundException.__lazy_accessor__js_class : (FileNotFoundException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/FileNotFoundException"), false, Exception.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), FileNotFoundException.generated34(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class InputStream {
	static generated56(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof InputStream.__lazy_accessor__js_class !== 'undefined' ? InputStream.__lazy_accessor__js_class : (InputStream.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/InputStream"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), InputStream.generated56(Function.construct(FlatString.construct5("readString")), Function.construct(FlatString.construct5("readBytes"))), this);
			})());
	}
}

class FileReader extends FlatObject {
	file = flat_null;

	static construct(file) {
		let __value = new FileReader();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FileReader.__assignments.apply(__value, [].slice.call(arguments));
		__value = FileReader.__init.call(__value, file);

		return __value;
	}

	destroy() {
	}

	readBytes() {
		return flat_null;
	}

	static __init(file) {
		let self = this;

		this.file = file;
		this.open();
		return self;
	}

	open() {
	}

	close() {
		return true;
	}

	readAllContents() {
		let size = 0;
		let buffer = flat_null;
		try {
			buffer = File.File_FileGlobal.accessor_fs().readFileSync(this.file.location.chars.data, 'utf8');
			size = buffer.length;
		} catch (err) {
			throw Exception.construct(FlatString.construct5("Could not read contents of file '").plus0((this.file.location).plus0(FlatString.construct5("'"))));
		}
		return FlatString.construct3(buffer, size);
	}

	readString() {
		return this.readAllContents();
	}

	readLine() {
		return this.readString();
	}

	readChar() {
		return 0;
	}

	static generated36(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated127(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated140(value0, value1, value2, value3, value4, value5, value6, value7, value8) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		return FlatArray.construct1(temp, 9);
	}

	static accessor__js_class() {
		return typeof FileReader.__lazy_accessor__js_class !== 'undefined' ? FileReader.__lazy_accessor__js_class : (FileReader.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/FileReader"), false, FlatObject.accessor__js_class(), FileReader.generated36(InputStream.accessor__js_class()), FileReader.generated127(Field.construct(FlatString.construct5("isOpen")), Field.construct(FlatString.construct5("file"))), FileReader.generated140(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("readBytes")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("open")), Function.construct(FlatString.construct5("close")), Function.construct(FlatString.construct5("readAllContents")), Function.construct(FlatString.construct5("readString")), Function.construct(FlatString.construct5("readLine")), Function.construct(FlatString.construct5("readChar"))), this);
			})());
	}

	accessor_isOpen() {
		return false;
	}

	static __assignments() {
	}
}

class FileWriter extends FlatObject {
	file = flat_null;

	static construct0(file) {
		let __value = new FileWriter();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FileWriter.__assignments.apply(__value, [].slice.call(arguments));
		__value = FileWriter.__init0.call(__value, file);

		return __value;
	}

	static construct1(location) {
		let __value = new FileWriter();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FileWriter.__assignments.apply(__value, [].slice.call(arguments));
		__value = FileWriter.__init1.call(__value, location);

		return __value;
	}

	static construct2() {
		let __value = new FileWriter();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FileWriter.__assignments.apply(__value, [].slice.call(arguments));
		__value = FileWriter.__init2.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init0(file) {
		let self = this;

		this.file = file;
		return self;
	}

	static __init1(location) {
		let self = this;

		self = FileWriter.__init0.call(this, File.construct1(location));
		return self;
	}

	writeLine(line) {
		line = typeof line === 'undefined' ? FlatString.construct5("") : line;
		return this.write((line).plus0(FlatString.construct5("\n")));
	}

	static __init2() {
		let self = this;

		return self;
	}

	write(data) {
		File.File_FileGlobal.accessor_fs().writeFileSync(this.file.location.chars.data, data.chars.data);
		return this;
	}

	append(data) {
		File.File_FileGlobal.accessor_fs().appendFileSync(this.file.location.chars.data, data.chars.data);
		return data;
	}

	static generated35(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated53(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated65(value0, value1, value2, value3, value4, value5, value6, value7, value8) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		return FlatArray.construct1(temp, 9);
	}

	static accessor__js_class() {
		return typeof FileWriter.__lazy_accessor__js_class !== 'undefined' ? FileWriter.__lazy_accessor__js_class : (FileWriter.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/FileWriter"), false, FlatObject.accessor__js_class(), FileWriter.generated35(OutputStream.accessor__js_class()), FileWriter.generated53(Field.construct(FlatString.construct5("isOpen")), Field.construct(FlatString.construct5("file"))), FileWriter.generated65(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("writeLine")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("append"))), this);
			})());
	}

	accessor_isOpen() {
		return false;
	}

	static __assignments() {
	}
}

class StreamIterator extends FlatObject {
	iterator = flat_null;

	static construct(iterator) {
		let __value = new StreamIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StreamIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = StreamIterator.__init.call(__value, iterator);

		return __value;
	}

	destroy() {
	}

	static __init(iterator) {
		let self = this;

		this.iterator = iterator;
		return self;
	}

	static generated105(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated119(value0, value1, value2, value3, value4) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		return FlatArray.construct1(temp, 5);
	}

	static generated200(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof StreamIterator.__lazy_accessor__js_class !== 'undefined' ? StreamIterator.__lazy_accessor__js_class : (StreamIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/stream/StreamIterator"), false, FlatObject.accessor__js_class(), StreamIterator.generated105(Iterator.accessor__js_class()), StreamIterator.generated119(Field.construct(FlatString.construct5("iterator")), Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("current"))), StreamIterator.generated200(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor_hasNext() {
		return false;
	}

	accessor_stepNext() {
		return flat_null;
	}

	accessor_next() {
		return flat_null;
	}

	accessor_current() {
		return flat_null;
	}

	static __assignments() {
	}
}

class FilterIterator extends StreamIterator {
	funcs = flat_null;

	static construct(iterator, func) {
		let __value = new FilterIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StreamIterator.__assignments.apply(__value, [].slice.call(arguments));
		FilterIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = FilterIterator.__init.call(__value, iterator, func);

		return __value;
	}

	destroy() {
	}

	static __init(iterator, func) {
		let self = this;

		this.funcs.add0(func);
		self = StreamIterator.__init.call(this, iterator);
		return self;
	}

	static generated44(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated214(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	accessor_hasNext() {
		let self = this;

		if (this.iterator.accessor_hasNext()) {
			while (!this.funcs.all0((_x, _i, _list) => {
						return _x(self.iterator.accessor_next());
			}, undefined)) {
				this.iterator.accessor_stepNext();
				if (!this.iterator.accessor_hasNext()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	static accessor__js_class() {
		return typeof FilterIterator.__lazy_accessor__js_class !== 'undefined' ? FilterIterator.__lazy_accessor__js_class : (FilterIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/stream/FilterIterator"), false, StreamIterator.accessor__js_class(), FlatArray.construct(), FilterIterator.generated44(Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("current"))), FilterIterator.generated214(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor_stepNext() {
		return this.iterator.accessor_stepNext();
	}

	accessor_next() {
		return this.iterator.accessor_next();
	}

	accessor_current() {
		return this.iterator.accessor_current();
	}

	static __assignments() {
		this.funcs = FlatArray.construct();
	}
}

class FilterNotIterator extends StreamIterator {
	funcs = flat_null;

	static construct(iterator, func) {
		let __value = new FilterNotIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StreamIterator.__assignments.apply(__value, [].slice.call(arguments));
		FilterNotIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = FilterNotIterator.__init.call(__value, iterator, func);

		return __value;
	}

	destroy() {
	}

	static __init(iterator, func) {
		let self = this;

		this.funcs.add0(func);
		self = StreamIterator.__init.call(this, iterator);
		return self;
	}

	static generated47(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated73(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	accessor_hasNext() {
		let self = this;

		if (this.iterator.accessor_hasNext()) {
			while (this.funcs.any0((_x, _i, _list) => {
						return _x(self.iterator.accessor_next());
			})) {
				this.iterator.accessor_stepNext();
				if (!this.iterator.accessor_hasNext()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	static accessor__js_class() {
		return typeof FilterNotIterator.__lazy_accessor__js_class !== 'undefined' ? FilterNotIterator.__lazy_accessor__js_class : (FilterNotIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/stream/FilterNotIterator"), false, StreamIterator.accessor__js_class(), FlatArray.construct(), FilterNotIterator.generated47(Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("current"))), FilterNotIterator.generated73(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor_stepNext() {
		return this.iterator.accessor_stepNext();
	}

	accessor_next() {
		return this.iterator.accessor_next();
	}

	accessor_current() {
		return this.iterator.accessor_current();
	}

	static __assignments() {
		this.funcs = FlatArray.construct();
	}
}

class FlatMapIterator extends StreamIterator {
	funcs = flat_null;
	_currentValue = flat_null;
	_nextValue = flat_null;
	_next = flat_null;

	static construct(iterator, func) {
		let __value = new FlatMapIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StreamIterator.__assignments.apply(__value, [].slice.call(arguments));
		FlatMapIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatMapIterator.__init.call(__value, iterator, func);

		return __value;
	}

	destroy() {
	}

	static __init(iterator, func) {
		let self = this;

		this.funcs.add0(func);
		self = StreamIterator.__init.call(this, iterator);
		return self;
	}

	mapNext() {
		let self = this;

		return this.funcs.map1((_x, _i, _array) => {
				return _x(self.iterator.accessor_next());
		}).accessor_first();
	}

	static generated216(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated231(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	accessor_hasNext() {
		if ((this._next) !== flat_null) {
			return true;
		} else if (this.iterator.accessor_hasNext()) {
			return true;
		} else {
			return false;
		}
	}

	accessor_stepNext() {
		let flat_local_0 = flat_null;
		let value = this.accessor_next();
		if (!((flat_local_0 = this._next) !== flat_null ? (flat_local_0.iterator.accessor_hasNext()) : false)) {
			this._next = flat_null;
			this.iterator.accessor_stepNext();
			return value;
		}
		this._currentValue = this._nextValue;
		this._nextValue = this._next.iterator.accessor_stepNext();
		return value;
	}

	accessor_next() {
		if ((this._next) !== flat_null) {
			return this._nextValue;
		}
		while (((this._next = this.mapNext())) !== flat_null && !this._next.iterator.accessor_hasNext()) {
			this.iterator.accessor_stepNext();
			if (!this.iterator.accessor_hasNext()) {
				return flat_null;
			}
		}
		this._currentValue = this._nextValue;
		this._nextValue = this._next.iterator.accessor_stepNext();
		return this._nextValue;
	}

	static accessor__js_class() {
		return typeof FlatMapIterator.__lazy_accessor__js_class !== 'undefined' ? FlatMapIterator.__lazy_accessor__js_class : (FlatMapIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/stream/FlatMapIterator"), false, StreamIterator.accessor__js_class(), FlatArray.construct(), FlatMapIterator.generated216(Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("current"))), FlatMapIterator.generated231(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("mapNext"))), this);
			})());
	}

	accessor_current() {
		return this._currentValue;
	}

	static __assignments() {
		this.funcs = FlatArray.construct();
		this._currentValue = flat_null;
		this._nextValue = flat_null;
		this._next = flat_null;
	}
}

class FlatFloat extends Number {
	value = 0;

	static construct(value) {
		let __value = new FlatFloat();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Primitive.__assignments.apply(__value, [].slice.call(arguments));
		Number.__assignments.apply(__value, [].slice.call(arguments));
		FlatFloat.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatFloat.__init.call(__value, value);

		return __value;
	}

	destroy() {
	}

	static __init(value) {
		let self = this;

		this.value = value;
		return self;
	}

	withinTolerance(target, tolerance) {
		return FlatFloat.withinTolerance(this.value, target, tolerance);
	}

	static withinTolerance(value, target, tolerance) {
		tolerance = typeof tolerance === 'undefined' ? 0.00000001 : tolerance;
		return (value - target) < tolerance && (value - target) > -tolerance;
	}

	compareTo0(other) {
		let flat_local_0 = flat_null;
		return (this.value - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	compareToReal(other) {
		return (this.value - other);
	}

	compareToInteger(other) {
		return (this.value - other);
	}

	static compareTo(value, other) {
		return (value - other);
	}

	static compareToReal(value, other) {
		return (value - other);
	}

	static compareToInteger(value, other) {
		return (value - other);
	}

	plus0(other) {
		let flat_local_0 = flat_null;
		return FlatFloat.construct(this.value + ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	plusReal(other) {
		return this.value + other;
	}

	plusInteger(other) {
		return (this.value + other);
	}

	plusEquals0(other) {
		let flat_local_0 = flat_null;
		return FlatFloat.construct(this.value += ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	plusEqualsReal(other) {
		return this.value += other;
	}

	plusEqualsInteger(other) {
		return (this.value += other);
	}

	minus(other) {
		let flat_local_0 = flat_null;
		return FlatFloat.construct(this.value - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	minusReal(other) {
		return this.value - other;
	}

	minusInteger(other) {
		return (this.value - other);
	}

	minusEquals(other) {
		let flat_local_0 = flat_null;
		return FlatFloat.construct(this.value -= ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	minusEqualsReal(other) {
		return this.value -= other;
	}

	minusEqualsInteger(other) {
		return (this.value -= other);
	}

	multiply0(other) {
		let flat_local_0 = flat_null;
		return FlatFloat.construct(this.value * ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	multiplyReal(other) {
		return this.value * other;
	}

	multiplyInteger(other) {
		return (this.value * other);
	}

	multiplyEquals(other) {
		let flat_local_0 = flat_null;
		return FlatFloat.construct(this.value *= ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	multiplyEqualsReal(other) {
		return this.value *= other;
	}

	multiplyEqualsInteger(other) {
		return (this.value *= other);
	}

	equals(other) {
		let flat_local_0 = flat_null;
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(Number.accessor__js_class()) && ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0) === this.value;
	}

	equals0(other) {
		return this.value === other;
	}

	equalsReal(other) {
		return this.value === other;
	}

	equalsInteger(other) {
		return this.value === other;
	}

	static equals(value, other) {
		return value === other;
	}

	static equalsReal(value, other) {
		return value === other;
	}

	static equalsInteger(value, other) {
		return value === other;
	}

	toString() {
		return FlatFloat.toString(this.value);
	}

	static numDigits(number) {
		return FlatDouble.numDigits(number);
	}

	static toString(value) {
		return FlatString.construct4(FlatFloat.toCharArray(value));
	}

	static toJson(value, format, tab) {
		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		return FlatFloat.toString(value);
	}

	static hashCodeLong(value) {
		return FlatMath.abs0(value);
	}

	static plus(a, b) {
		return a + b;
	}

	static parseFloat(str) {
		return parseFloat(str.chars.data);
	}

	static toCharArray(value) {
		return value.toString();
	}

	toFixed(precision) {
		return FlatFloat.toFixed(this.value, precision);
	}

	static toFixed(value, precision) {
		return FlatDouble.toFixed(value, precision);
	}

	static generated228(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated234(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated241(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		temp[41] = value41;
		temp[42] = value42;
		temp[43] = value43;
		temp[44] = value44;
		return FlatArray.construct1(temp, 45);
	}

	static accessor__js_class() {
		return typeof FlatFloat.__lazy_accessor__js_class !== 'undefined' ? FlatFloat.__lazy_accessor__js_class : (FlatFloat.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/primitive/number/Float"), false, Number.accessor__js_class(), FlatFloat.generated228(RealNumber.accessor__js_class()), FlatFloat.generated234(Field.construct(FlatString.construct5("realValue")), Field.construct(FlatString.construct5("integerValue")), Field.construct(FlatString.construct5("value"))), FlatFloat.generated241(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("withinTolerance")), Function.construct(FlatString.construct5("withinTolerance")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("plusReal")), Function.construct(FlatString.construct5("plusInteger")), Function.construct(FlatString.construct5("plusEquals")), Function.construct(FlatString.construct5("plusEqualsReal")), Function.construct(FlatString.construct5("plusEqualsInteger")), Function.construct(FlatString.construct5("minus")), Function.construct(FlatString.construct5("minusReal")), Function.construct(FlatString.construct5("minusInteger")), Function.construct(FlatString.construct5("minusEquals")), Function.construct(FlatString.construct5("minusEqualsReal")), Function.construct(FlatString.construct5("minusEqualsInteger")), Function.construct(FlatString.construct5("multiply")), Function.construct(FlatString.construct5("multiplyReal")), Function.construct(FlatString.construct5("multiplyInteger")), Function.construct(FlatString.construct5("multiplyEquals")), Function.construct(FlatString.construct5("multiplyEqualsReal")), Function.construct(FlatString.construct5("multiplyEqualsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("numDigits")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("hashCodeLong")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("parseFloat")), Function.construct(FlatString.construct5("toCharArray")), Function.construct(FlatString.construct5("toFixed")), Function.construct(FlatString.construct5("toFixed"))), this);
			})());
	}

	accessor_realValue() {
		return this.value;
	}

	accessor_integerValue() {
		return this.value;
	}

	static __assignments() {
	}
}

class Function extends FlatObject {
	name = flat_null;

	static construct(name) {
		let __value = new Function();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Function.__assignments.apply(__value, [].slice.call(arguments));
		__value = Function.__init.call(__value, name);

		return __value;
	}

	static __init(name) {
		let self = this;

		this.name = name;
		return self;
	}

	call(obj, args) {
		args = typeof args === 'undefined' ? FlatArray.construct() : args;
		return obj[this.name.chars.data].apply(obj, args.data);
	}

	callStatic(c, args) {
		args = typeof args === 'undefined' ? FlatArray.construct() : args;
		return c.jsClass[this.name.chars.data].apply(null, args.data);
	}

	copy(name) {
		name = typeof name === 'undefined' ? this.name : name;
		return Function.construct(name);
	}

	equals(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(Function.accessor__js_class()) && this.equals0(other);
	}

	equals0(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && (this.name.equals(other.name));
	}

	toString() {
		return FlatString.construct5("Function {\n  \"name\": ").plus0((this.name !== flat_null && this.name.accessor__js_class().isOfType(FlatString.accessor__js_class()) ? Char.toString('"').plus0(this.name.toString().plus0(Char.toString('"'))) : this.name.toString()).plus0(FlatString.construct5("\n}")));
	}

	toJson(format, tab) {
		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		return format ? FlatString.construct5("{\n").plus0((tab).plus0(FlatString.construct5("\"name\":").plus0((format ? FlatString.construct5(" ") : FlatString.construct5("")).plus0(FlatString.construct5("").plus0((this.name.toJson(format, tab)).plus0(FlatString.construct5("\n}"))))))) : FlatString.construct5("{\"name\":").plus0((format ? FlatString.construct5(" ") : FlatString.construct5("")).plus0(FlatString.construct5("").plus0((this.name.toJson(format, tab)).plus0(FlatString.construct5("}")))));
	}

	toBuilder() {
		return flat_meta_Function_Builder.construct(this.name);
	}

	copyTo(target, name) {
		name = typeof name === 'undefined' ? this.name : name;
		return target.toBuilder().name(name).build();
	}

	static generated211(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated224(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		return FlatArray.construct1(temp, 11);
	}

	static accessor__js_class() {
		return typeof Function.__lazy_accessor__js_class !== 'undefined' ? Function.__lazy_accessor__js_class : (Function.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/meta/Function"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Function.generated211(Field.construct(FlatString.construct5("name"))), Function.generated224(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("call")), Function.construct(FlatString.construct5("callStatic")), Function.construct(FlatString.construct5("copy")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("toBuilder")), Function.construct(FlatString.construct5("copyTo"))), this);
			})());
	}

	static __assignments() {
	}
	static flat_meta_Function_Builder
}

class flat_meta_Function_Builder extends FlatObject {
	name_value = flat_null;

	static construct(name) {
		let __value = new flat_meta_Function_Builder();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		flat_meta_Function_Builder.__assignments.apply(__value, [].slice.call(arguments));
		__value = flat_meta_Function_Builder.__init.call(__value, name);

		return __value;
	}

	name(value) {
		this.name_value = value;
		return this;
	}

	build() {
		return Function.construct(this.name_value);
	}

	static __init(name) {
		let self = this;

		name = typeof name === 'undefined' ? this.name_value : name;
		this.name_value = name;
		return self;
	}

	static generated238(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static accessor__js_class() {
		return typeof flat_meta_Function_Builder.__lazy_accessor__js_class !== 'undefined' ? flat_meta_Function_Builder.__lazy_accessor__js_class : (flat_meta_Function_Builder.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/meta/Function.Builder"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), flat_meta_Function_Builder.generated238(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("name")), Function.construct(FlatString.construct5("build")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class Future extends FlatObject {
	promiseValue = flat_null;
	resolveFunc = flat_null;
	rejectFunc = flat_null;
	completed = false;
	cancelled = false;

	static construct() {
		let __value = new Future();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Future.__assignments.apply(__value, [].slice.call(arguments));
		__value = Future.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	async get() {
		return this.promiseValue
		.then((data) => {
				this.completed = true;
				if (this.cancelled) {
					throw CancellationException.construct(undefined);
				}
				return data;
		})
		.catch((err) => {
				this.completed = true;
				if (this.cancelled) {
					throw CancellationException.construct(undefined);
				}
				return err;
		});
	}

	cancel() {
		this.completed = true;
		this.cancelled = true;
		this.resolveFunc();
	}

	static from(func) {
		let future = Future.construct();
		(async () => {
				try {
					future.resolveFunc(await func());
				} catch (e) {
					future.rejectFunc(e);
				}
		})();
		return future;
	}

	static fromResolve(func) {
		let future = Future.construct();
		let res = future.resolveFunc;
		let rej = future.rejectFunc;
		func(res, rej);
		return future;
	}

	static create() {
		return Future_CompletableFuture.construct();
	}

	static __init() {
		let self = this;

		this.promiseValue = new Promise((resolve, reject) => {
				this.resolveFunc = resolve;
				this.rejectFunc = reject;
		});
		return self;
	}

	static generated219(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated233(value0, value1, value2, value3, value4, value5, value6) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		return FlatArray.construct1(temp, 7);
	}

	static accessor__js_class() {
		return typeof Future.__lazy_accessor__js_class !== 'undefined' ? Future.__lazy_accessor__js_class : (Future.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/future/Future"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Future.generated219(Field.construct(FlatString.construct5("completed")), Field.construct(FlatString.construct5("cancelled"))), Future.generated233(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("get")), Function.construct(FlatString.construct5("cancel")), Function.construct(FlatString.construct5("from")), Function.construct(FlatString.construct5("fromResolve")), Function.construct(FlatString.construct5("create")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
	static Future_CompletableFuture
}

class Future_CompletableFuture extends Future {
	static construct() {
		let __value = new Future_CompletableFuture();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Future.__assignments.apply(__value, [].slice.call(arguments));
		Future_CompletableFuture.__assignments.apply(__value, [].slice.call(arguments));
		__value = Future_CompletableFuture.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		self = Future.__init.call(this);
		return self;
	}

	complete(value) {
		this.resolveFunc(value);
		return value;
	}

	error(value) {
		this.rejectFunc(value);
		return value;
	}

	static generated240(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static accessor__js_class() {
		return typeof Future_CompletableFuture.__lazy_accessor__js_class !== 'undefined' ? Future_CompletableFuture.__lazy_accessor__js_class : (Future_CompletableFuture.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/future/Future.CompletableFuture"), false, Future.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), Future_CompletableFuture.generated240(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("complete")), Function.construct(FlatString.construct5("error"))), this);
			})());
	}

	static __assignments() {
	}
}

class HashMap extends FlatObject {
	buckets = flat_null;
	bucketSize = 0;
	count = 0;
	first = flat_null;
	last = flat_null;

	static construct0(bucketCount, bucketSize) {
		let __value = new HashMap();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		HashMap.__assignments.apply(__value, [].slice.call(arguments));
		__value = HashMap.__init0.call(__value, bucketCount, bucketSize);

		return __value;
	}

	static construct1(source, bucketCount, bucketSize) {
		let __value = new HashMap();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		HashMap.__assignments.apply(__value, [].slice.call(arguments));
		__value = HashMap.__init1.call(__value, source, bucketCount, bucketSize);

		return __value;
	}

	static construct2(entries, bucketCount, bucketSize) {
		let __value = new HashMap();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		HashMap.__assignments.apply(__value, [].slice.call(arguments));
		__value = HashMap.__init2.call(__value, entries, bucketCount, bucketSize);

		return __value;
	}

	destroy() {
	}

	static __init0(bucketCount, bucketSize) {
		let self = this;

		bucketCount = typeof bucketCount === 'undefined' ? 5 : bucketCount;
		bucketSize = typeof bucketSize === 'undefined' ? 5 : bucketSize;
		this.bucketSize = bucketSize;
		this.buckets = FlatArray.construct0(bucketCount, undefined).map1((_x, _i, _array) => {
				return FlatArray.construct0(bucketSize, undefined);
		});
		return self;
	}

	static __init1(source, bucketCount, bucketSize) {
		let self = this;

		bucketCount = typeof bucketCount === 'undefined' ? 5 : bucketCount;
		bucketSize = typeof bucketSize === 'undefined' ? 5 : bucketSize;
		this.bucketSize = bucketSize;
		self = HashMap.__init2.call(this, source.accessor_entries(), bucketCount, bucketSize);
		return self;
	}

	static __init2(entries, bucketCount, bucketSize) {
		let self = this;

		bucketCount = typeof bucketCount === 'undefined' ? 5 : bucketCount;
		bucketSize = typeof bucketSize === 'undefined' ? 5 : bucketSize;
		this.bucketSize = bucketSize;
		self = HashMap.__init0.call(this, bucketCount, bucketSize);
		entries.forEach1((_x, _i, _array) => {
				self.add0(_x.key, _x.value);
		});
		return self;
	}

	add0(key, value) {
		return this.set(key, value);
	}

	add1(pair) {
		return this.set(pair.key, pair.value);
	}

	addAll0(pairs) {
		let self = this;

		return pairs.forEach1((_x, _i, _list) => {
				self.add1(_x);
		});
	}

	addAll1(map) {
		let self = this;

		return map.accessor_entries().forEach1((_x, _i, _array) => {
				self.add1(_x);
		});
	}

	getOrDefault(key, defaultValue) {
		return this.containsKey(key) ? this.get(key) : this.add0(key, defaultValue());
	}

	getAndRemove(key) {
		if (this.containsKey(key)) {
			let value = this.get(key);
			this.remove(key);
			return value;
		} else {
			return flat_null;
		}
	}

	toArray() {
		let self = this;

		return this.map1((_x, _i, _map) => {
				return _x;
		});
	}

	copy() {
		return HashMap.construct2(this.toArray(), undefined, undefined);
	}

	contains0(value) {
		if (!((value.key) !== flat_null)) {
			return false;
		}
		let pair = this.getPair(value.key);
		return (pair) !== flat_null && pair.value.equals(value.value);
	}

	any0(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let pair = flat_null;
				pair = flat_local_1.accessor_stepNext();
				if ((pair) !== flat_null && func(pair, i++, this)) {
					return true;
				}
			}
		}
		return false;
	}

	all0(func, stopOnContradiction) {
		let flat_local_0 = flat_null;
		stopOnContradiction = typeof stopOnContradiction === 'undefined' ? true : stopOnContradiction;
		let contradiction = false;
		let i = 0;
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let pair = flat_null;
				pair = flat_local_1.accessor_stepNext();
				if ((pair) !== flat_null && !func(pair, i++, this)) {
					if (stopOnContradiction) {
						return false;
					}
					contradiction = true;
				}
			}
		}
		return !contradiction;
	}

	map1(func) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let pair = flat_null;
				pair = flat_local_1.accessor_stepNext();
				if ((pair) !== flat_null) {
					array.add0(func(pair, i++, this));
				}
			}
		}
		return array;
	}

	mapValues(func) {
		let self = this;

		let map = HashMap.construct0(undefined, undefined);
		this.forEach1((_x, _i, _map) => {
				map.add0(_x.key, func(_x, _i, this));
		});
		return map;
	}

	filter1(func) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let pair = flat_null;
				pair = flat_local_1.accessor_stepNext();
				if ((pair) !== flat_null && func(pair, i++, this)) {
					array.add0(pair);
				}
			}
		}
		return array;
	}

	join(delimiter, maxOutputCount, ellipsePosition, ellipse) {
		let flat_local_0 = flat_null;
		delimiter = typeof delimiter === 'undefined' ? FlatString.construct5("") : delimiter;
		maxOutputCount = typeof maxOutputCount === 'undefined' ? this.count : maxOutputCount;
		ellipsePosition = typeof ellipsePosition === 'undefined' ? ~~(maxOutputCount / 2 + 1) : ellipsePosition;
		ellipse = typeof ellipse === 'undefined' ? FlatString.construct5("...") : ellipse;
		let i = 0;
		let output = FlatString.construct5("");
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let pair = flat_null;
				pair = flat_local_1.accessor_stepNext();
				if ((pair) !== flat_null) {
					if (i > 0) {
						output = output.plus0(delimiter);
					}
					output = output.plus0(pair.toString());
				}
			}
		}
		return output;
	}

	skip(num) {
		let self = this;

		return this.map1((_x, _i, _map) => {
				return _x;
		}).skip(num);
	}

	take(num) {
		let self = this;

		return this.map1((_x, _i, _map) => {
				return _x;
		}).take(num);
	}

	reverse() {
		let self = this;

		return this.map1((_x, _i, _map) => {
				return _x;
		}).reverse();
	}

	firstWhere0(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let pair = flat_null;
				pair = flat_local_1.accessor_stepNext();
				if ((pair) !== flat_null && func(pair, i++, this)) {
					return pair;
				}
			}
		}
		return flat_null;
	}

	forEach1(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let pair = flat_null;
				pair = flat_local_1.accessor_stepNext();
				if ((pair) !== flat_null) {
					func(pair, i++, this);
				}
			}
		}
		return this;
	}

	getBucket(key) {
		return this.buckets.get((key.accessor_hashCodeLong() & (this.buckets.accessor_count() - 1)));
	}

	getPair(key) {
		let self = this;

		return this.getBucket(key).filter1((_x, _i, _array) => {
				return (_x) !== flat_null && _x.key.equals(key);
		}).accessor_first();
	}

	remove(key) {
		let flat_local_0 = flat_null;
		if (!((key) !== flat_null)) {
			return flat_null;
		}
		let bucket = this.getBucket(key);
		let i = 0;
		flat_local_0 = (bucket).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let pair = flat_null;
			pair = flat_local_0.accessor_stepNext();
			if ((pair) !== flat_null && pair.key.equals(key)) {
				bucket.remove0(i);
				this.count--;
				return pair.value;
			} else {
				i++;
			}
		}
		return flat_null;
	}

	removeAt(index) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let pair = flat_null;
				pair = flat_local_1.accessor_stepNext();
				if ((pair) !== flat_null) {
					if (i === index) {
						bucket.remove0(i);
						return pair;
					}
					i++;
				}
			}
		}
		return flat_null;
	}

	removeWhere(func) {
		let self = this;

		return this.filter1((_x, _i, _map) => {
				return func(_x, _i, this);
		}).forEach1((_x, _i, _array) => {
				self.remove(_x.key);
		});
	}

	containsKey(key) {
		return (key) !== flat_null && (this.getPair(key)) !== flat_null;
	}

	clone() {
		return HashMap.construct1(this, undefined, undefined);
	}

	toString() {
		return (HashMap.accessor__js_class().location).plus0(FlatString.construct5(" ").plus0((this.toJson(undefined, undefined)).plus0(FlatString.construct5(""))));
	}

	toJson(format, tab) {
		let self = this;

		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		if (format) {
			let values = this.map1((_x, _i, _map) => {
					return (_x.key.toString().toJson(undefined, undefined)).plus0(FlatString.construct5(": ").plus0((_x.value.toJson(format, tab)).plus0(FlatString.construct5(""))));
			}).join(FlatString.construct5(",\n"), undefined, undefined, undefined).indent(undefined, tab, undefined);
			return FlatString.construct5("{\n").plus0((values).plus0(FlatString.construct5("\n}")));
		} else {
			let values = this.map1((_x, _i, _map) => {
					return (_x.key.toString().toJson(undefined, undefined)).plus0(FlatString.construct5(":").plus0((_x.value.toJson(format, tab)).plus0(FlatString.construct5(""))));
			}).join(FlatString.construct5(","), undefined, undefined, undefined);
			return FlatString.construct5("{").plus0((values).plus0(FlatString.construct5("}")));
		}
	}

	equals(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(HashMap.accessor__js_class()) && this.equals0(other);
	}

	equals0(other) {
		let flat_local_0 = flat_null;
		if (this.count !== other.count) {
			return false;
		}
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			let p = element;
			if (!other.contains0(p)) {
				return false;
			}
		}
		return true;
	}

	get(key) {
		let flat_local_0 = flat_null;
		return (key) !== flat_null ? ((flat_local_0 = this.getPair(key)) !== flat_null ? (flat_local_0.value) : flat_null) : flat_null;
	}

	set(key, value) {
		if (!((key) !== flat_null)) {
			return flat_null;
		}
		this.remove(key);
		this.getBucket(key).add0(Pair.construct(key, value));
		this.count++;
		return value;
	}

	static jsObjectToFlatHashMap(obj) {
		let key = flat_null;
		let nativeValue = flat_null;
		let value = flat_null;
		let map = HashMap.construct0(undefined, undefined);
		Object.entries(obj).forEach((entry) => {
				key = entry[0];
				nativeValue = entry[1];
				value = HashMap.fromJsonValue(nativeValue);
				map.add0(FlatString.construct4(key), value);
		});
		return map;
	}

	static fromJsonValue(obj) {
		let value = flat_null;
		let data = flat_null;
		let arrayData = flat_null;
		let arrayCount = 0;
		let num = 0;
		let boolValue = false;
		if (typeof obj === 'string') {
			data = obj;
			value = FlatString.construct4(data);
		} else if (typeof obj === 'number') {
			num = obj;
			value = FlatLong.construct(num);
		} else if (typeof obj === 'boolean') {
			boolValue = obj;
			value = Bool.construct(boolValue);
		} else if (Array.isArray(obj)) {
			arrayData = obj.map(o => {
					value = o;
					return HashMap.fromJsonValue(value);
			});
			arrayCount = obj.length;
			value = FlatArray.construct1(arrayData, arrayCount);
		} else if (obj === null) {
			value = flat_null;
		} else if (typeof obj === 'object') {
			value = obj;
			value = HashMap.jsObjectToFlatHashMap(value);
		} else if (typeof obj === 'undefined') {
			value = flat_null;
		} else {
			throw new Error("Invalid type: " + (typeof obj));
		}
		return value;
	}

	static toJsonArray(array) {
		let jsArray = flat_null;
		let data = flat_null;
		jsArray = array.data.map((value) => {
				data = value;
				return HashMap.fromJsonValue(data);
		});
		return jsArray;
	}

	static toJsonValue(obj) {
		if (obj === flat_null) {
			return undefined;
		} else if (obj.accessor__js_class().isOfType(FlatString.accessor__js_class())) {
			let str = obj;
			return str.chars.data;
		} else if (obj.accessor__js_class().isOfType(Primitive.accessor__js_class())) {
			return obj.value;
		} else if (obj.accessor__js_class().isOfType(FlatArray.accessor__js_class())) {
			return HashMap.toJsonArray(obj);
		} else if (obj.accessor__js_class().isOfType(HashMap.accessor__js_class())) {
			let map = obj;
			return map.toJsObject();
		} else {
			throw Exception.construct(FlatString.construct5("Invalid type: ").plus0((obj.accessor__js_class()).toString().plus0(FlatString.construct5(""))));
		}
		return flat_null;
	}

	toJsObject() {
		let self = this;

		let map = flat_null;
		map = {};
		this.forEach1((_x, _i, _map) => {
				map[HashMap.toJsonValue(_x.key)] = HashMap.toJsonValue(_x.value);
		});
		return map;
	}

	static generated50(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated58(value0, value1, value2, value3, value4) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		return FlatArray.construct1(temp, 5);
	}

	static generated104(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		temp[41] = value41;
		temp[42] = value42;
		temp[43] = value43;
		return FlatArray.construct1(temp, 44);
	}

	static accessor__js_class() {
		return typeof HashMap.__lazy_accessor__js_class !== 'undefined' ? HashMap.__lazy_accessor__js_class : (HashMap.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/HashMap"), false, FlatObject.accessor__js_class(), HashMap.generated50(List.accessor__js_class()), HashMap.generated58(Field.construct(FlatString.construct5("count")), Field.construct(FlatString.construct5("entries")), Field.construct(FlatString.construct5("keys")), Field.construct(FlatString.construct5("values")), Field.construct(FlatString.construct5("iterator"))), HashMap.generated104(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("add")), Function.construct(FlatString.construct5("add")), Function.construct(FlatString.construct5("addAll")), Function.construct(FlatString.construct5("addAll")), Function.construct(FlatString.construct5("getOrDefault")), Function.construct(FlatString.construct5("getAndRemove")), Function.construct(FlatString.construct5("toArray")), Function.construct(FlatString.construct5("copy")), Function.construct(FlatString.construct5("contains")), Function.construct(FlatString.construct5("any")), Function.construct(FlatString.construct5("all")), Function.construct(FlatString.construct5("map")), Function.construct(FlatString.construct5("mapValues")), Function.construct(FlatString.construct5("filter")), Function.construct(FlatString.construct5("join")), Function.construct(FlatString.construct5("skip")), Function.construct(FlatString.construct5("take")), Function.construct(FlatString.construct5("reverse")), Function.construct(FlatString.construct5("firstWhere")), Function.construct(FlatString.construct5("forEach")), Function.construct(FlatString.construct5("getBucket")), Function.construct(FlatString.construct5("getPair")), Function.construct(FlatString.construct5("remove")), Function.construct(FlatString.construct5("removeAt")), Function.construct(FlatString.construct5("removeWhere")), Function.construct(FlatString.construct5("containsKey")), Function.construct(FlatString.construct5("clone")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("get")), Function.construct(FlatString.construct5("set")), Function.construct(FlatString.construct5("jsObjectToFlatHashMap")), Function.construct(FlatString.construct5("fromJsonValue")), Function.construct(FlatString.construct5("toJsonArray")), Function.construct(FlatString.construct5("toJsonValue")), Function.construct(FlatString.construct5("toJsObject"))), this);
			})());
	}

	accessor_entries() {
		let self = this;

		return this.filter1((_x, _i, _map) => {
				return true;
		});
	}

	accessor_keys() {
		let self = this;

		return this.map1((_x, _i, _map) => {
				return _x.key;
		});
	}

	accessor_values() {
		let self = this;

		return this.map1((_x, _i, _map) => {
				return _x.value;
		});
	}

	accessor_iterator() {
		return HashMap_HashMapIterator.construct(this);
	}

	accessor_first() {
		return this.first;
	}

	mutator_first(value) {
		this.first = value;
		return value;
	}

	accessor_last() {
		return this.last;
	}

	mutator_last(value) {
		this.last = value;
		return value;
	}

	static __assignments() {
	}
	static HashMap_HashMapIterator
}

class HashMap_HashMapIterator extends FlatObject {
	bucketIterator = flat_null;
	valueIterator = flat_null;
	position = 0;
	map = flat_null;

	static construct(map) {
		let __value = new HashMap_HashMapIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		HashMap_HashMapIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = HashMap_HashMapIterator.__init.call(__value, map);

		return __value;
	}

	destroy() {
	}

	static __init(map) {
		let self = this;

		this.map = map;
		this.reset();
		return self;
	}

	reset() {
		this.position = 0;
		this.bucketIterator = this.map.buckets.accessor_iterator();
		this.valueIterator = this.bucketIterator.accessor_hasNext() ? this.bucketIterator.accessor_stepNext().accessor_iterator() : flat_null;
		return this;
	}

	static generated229(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated237(value0, value1, value2, value3, value4, value5, value6, value7) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		return FlatArray.construct1(temp, 8);
	}

	static generated239(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	accessor_stepNext() {
		let flat_local_0 = flat_null;
		if (((flat_local_0 = this.valueIterator) !== flat_null ? (flat_local_0.accessor_hasNext()) : false)) {
			this.position++;
			return this.valueIterator.accessor_stepNext();
		} else if (this.bucketIterator.accessor_hasNext()) {
			this.position++;
			this.valueIterator = this.bucketIterator.accessor_stepNext().accessor_iterator();
			return this.valueIterator.accessor_stepNext();
		}
		throw NoSuchElementException.construct(undefined);
	}

	accessor_previous() {
		let flat_local_0 = flat_null;
		if (((flat_local_0 = this.valueIterator) !== flat_null ? (flat_local_0.accessor_hasPrevious()) : false)) {
			return this.valueIterator.accessor_previous();
		} else if (this.bucketIterator.accessor_hasPrevious()) {
			return this.bucketIterator.accessor_previous().accessor_last();
		} else {
			return flat_null;
		}
	}

	accessor_next() {
		let flat_local_0 = flat_null;
		if (((flat_local_0 = this.valueIterator) !== flat_null ? (flat_local_0.accessor_hasNext()) : false)) {
			return this.valueIterator.accessor_next();
		} else if (this.bucketIterator.accessor_hasNext()) {
			return this.bucketIterator.accessor_next().accessor_first();
		} else {
			return flat_null;
		}
	}

	static accessor__js_class() {
		return typeof HashMap_HashMapIterator.__lazy_accessor__js_class !== 'undefined' ? HashMap_HashMapIterator.__lazy_accessor__js_class : (HashMap_HashMapIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/HashMap.HashMapIterator"), false, FlatObject.accessor__js_class(), HashMap_HashMapIterator.generated229(Iterator.accessor__js_class()), HashMap_HashMapIterator.generated237(Field.construct(FlatString.construct5("position")), Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("hasPrevious")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("current")), Field.construct(FlatString.construct5("previous")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("map"))), HashMap_HashMapIterator.generated239(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("reset"))), this);
			})());
	}

	accessor_hasNext() {
		let flat_local_0 = flat_null;
		return this.position < this.map.count && (this.bucketIterator.accessor_hasNext() || ((flat_local_0 = this.valueIterator) !== flat_null ? (flat_local_0.accessor_hasNext()) : false));
	}

	accessor_hasPrevious() {
		let flat_local_0 = flat_null;
		return this.bucketIterator.accessor_hasPrevious() || ((flat_local_0 = this.valueIterator) !== flat_null ? (flat_local_0.accessor_hasPrevious()) : false);
	}

	accessor_current() {
		let flat_local_0 = flat_null;
		return ((flat_local_0 = this.valueIterator) !== flat_null ? (flat_local_0.accessor_current()) : flat_null);
	}

	static __assignments() {
	}
}

class HashSet extends FlatObject {
	buckets = flat_null;
	bucketSize = 0;
	count = 0;

	static construct0(bucketCount, bucketSize) {
		let __value = new HashSet();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		HashSet.__assignments.apply(__value, [].slice.call(arguments));
		__value = HashSet.__init0.call(__value, bucketCount, bucketSize);

		return __value;
	}

	static construct1(values, bucketCount, bucketSize) {
		let __value = new HashSet();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		HashSet.__assignments.apply(__value, [].slice.call(arguments));
		__value = HashSet.__init1.call(__value, values, bucketCount, bucketSize);

		return __value;
	}

	destroy() {
	}

	static __init0(bucketCount, bucketSize) {
		let self = this;

		bucketCount = typeof bucketCount === 'undefined' ? 5 : bucketCount;
		bucketSize = typeof bucketSize === 'undefined' ? 5 : bucketSize;
		this.bucketSize = bucketSize;
		this.buckets = FlatArray.construct0(bucketCount, undefined).map1((_x, _i, _array) => {
				return FlatArray.construct0(bucketSize, undefined);
		});
		return self;
	}

	static __init1(values, bucketCount, bucketSize) {
		let self = this;

		bucketCount = typeof bucketCount === 'undefined' ? 5 : bucketCount;
		bucketSize = typeof bucketSize === 'undefined' ? 5 : bucketSize;
		this.bucketSize = bucketSize;
		self = HashSet.__init0.call(this, bucketCount, bucketSize);
		values.forEach1((_x, _i, _array) => {
				self.add(_x);
		});
		return self;
	}

	toArray() {
		let self = this;

		return this.map1((_1, _2, _3) => {
				return _1;
		});
	}

	any0(anyFunc) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let v = flat_null;
				v = flat_local_1.accessor_stepNext();
				if ((v) !== flat_null && anyFunc(v, i++, this)) {
					return true;
				}
			}
		}
		return false;
	}

	all0(allFunc, stopOnContradiction) {
		let flat_local_0 = flat_null;
		stopOnContradiction = typeof stopOnContradiction === 'undefined' ? true : stopOnContradiction;
		let contradiction = false;
		let i = 0;
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let v = flat_null;
				v = flat_local_1.accessor_stepNext();
				if ((v) !== flat_null && !allFunc(v, i++, this)) {
					if (stopOnContradiction) {
						return false;
					}
					contradiction = true;
				}
			}
		}
		return !contradiction;
	}

	map1(func) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let v = flat_null;
				v = flat_local_1.accessor_stepNext();
				if ((v) !== flat_null) {
					array.add0(func(v, i++, this));
				}
			}
		}
		return array;
	}

	filter1(func) {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		let i = 0;
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let v = flat_null;
				v = flat_local_1.accessor_stepNext();
				if ((v) !== flat_null && func(v, i++, this)) {
					array.add0(v);
				}
			}
		}
		return array;
	}

	join(delimiter, maxOutputCount, ellipsePosition, ellipse) {
		let flat_local_0 = flat_null;
		delimiter = typeof delimiter === 'undefined' ? FlatString.construct5("") : delimiter;
		maxOutputCount = typeof maxOutputCount === 'undefined' ? this.accessor_count() : maxOutputCount;
		ellipsePosition = typeof ellipsePosition === 'undefined' ? ~~(maxOutputCount / 2 + 1) : ellipsePosition;
		ellipse = typeof ellipse === 'undefined' ? FlatString.construct5("...") : ellipse;
		let i = 0;
		let output = FlatString.construct5("");
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let v = flat_null;
				v = flat_local_1.accessor_stepNext();
				if ((v) !== flat_null) {
					if (i > 0) {
						output = output.plus0(delimiter);
					}
					output = output.plus0(v.toString());
				}
			}
		}
		return output;
	}

	skip(num) {
		let self = this;

		return this.map1((_1, _2, _3) => {
				return _1;
		}).skip(num);
	}

	take(num) {
		let self = this;

		return this.map1((_1, _2, _3) => {
				return _1;
		}).take(num);
	}

	reverse() {
		let self = this;

		return this.map1((_1, _2, _3) => {
				return _1;
		}).reverse();
	}

	firstWhere0(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let v = flat_null;
				v = flat_local_1.accessor_stepNext();
				if ((v) !== flat_null && func(v, i++, this)) {
					return v;
				}
			}
		}
		return flat_null;
	}

	forEach1(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this.buckets).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let bucket = flat_null;
			let flat_local_1 = flat_null;
			bucket = flat_local_0.accessor_stepNext();
			flat_local_1 = (bucket).accessor_iterator();
			while (flat_local_1.accessor_hasNext()) {
				let v = flat_null;
				v = flat_local_1.accessor_stepNext();
				if ((v) !== flat_null) {
					func(v, i++, this);
				}
			}
		}
		return this;
	}

	getBucket(value) {
		return this.buckets.get((value.accessor_hashCodeLong() & (this.buckets.accessor_count() - 1)));
	}

	add(value) {
		this.addIfNotExists(value);
		return value;
	}

	addIfNotExists(value) {
		let bucket = this.getBucket(value);
		if (!bucket.contains0(value)) {
			bucket.add0(value);
			this.mutator_count(this.accessor_count() + 1);
			return true;
		}
		return false;
	}

	get(value) {
		let self = this;

		return this.getBucket(value).firstWhere0((_x, _i, _list) => {
				return (_x) !== flat_null && _x.equals(value);
		});
	}

	remove(value) {
		let flat_local_0 = flat_null;
		let bucket = this.getBucket(value);
		let i = 0;
		flat_local_0 = (bucket).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let v = flat_null;
			v = flat_local_0.accessor_stepNext();
			if ((v) !== flat_null && v.equals(value)) {
				bucket.remove0(i);
				this.mutator_count(this.accessor_count() - 1);
				return v;
			}
			i++;
		}
		return flat_null;
	}

	contains0(value) {
		return (value) !== flat_null && (this.get(value)) !== flat_null;
	}

	toString() {
		return FlatString.construct5("HashSet [").plus0((this.join(FlatString.construct5(", "), undefined, undefined, undefined)).plus0(FlatString.construct5("]")));
	}

	equals(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(HashSet.accessor__js_class()) && this.equals0(other);
	}

	equals0(other) {
		let flat_local_0 = flat_null;
		if (this.accessor_count() !== other.accessor_count()) {
			return false;
		}
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			let obj = element;
			if (!other.contains0(obj)) {
				return false;
			}
		}
		return true;
	}

	static generated49(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated55(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated88(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		return FlatArray.construct1(temp, 24);
	}

	static accessor__js_class() {
		return typeof HashSet.__lazy_accessor__js_class !== 'undefined' ? HashSet.__lazy_accessor__js_class : (HashSet.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/HashSet"), false, FlatObject.accessor__js_class(), HashSet.generated49(List.accessor__js_class()), HashSet.generated55(Field.construct(FlatString.construct5("count")), Field.construct(FlatString.construct5("iterator")), Field.construct(FlatString.construct5("first")), Field.construct(FlatString.construct5("last"))), HashSet.generated88(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("toArray")), Function.construct(FlatString.construct5("any")), Function.construct(FlatString.construct5("all")), Function.construct(FlatString.construct5("map")), Function.construct(FlatString.construct5("filter")), Function.construct(FlatString.construct5("join")), Function.construct(FlatString.construct5("skip")), Function.construct(FlatString.construct5("take")), Function.construct(FlatString.construct5("reverse")), Function.construct(FlatString.construct5("firstWhere")), Function.construct(FlatString.construct5("forEach")), Function.construct(FlatString.construct5("getBucket")), Function.construct(FlatString.construct5("add")), Function.construct(FlatString.construct5("addIfNotExists")), Function.construct(FlatString.construct5("get")), Function.construct(FlatString.construct5("remove")), Function.construct(FlatString.construct5("contains")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals"))), this);
			})());
	}

	accessor_count() {
		return this.count;
	}

	mutator_count(value) {
		this.count = value;
		return value;
	}

	accessor_iterator() {
		return HashSet_HashSetIterator.construct(this);
	}

	accessor_first() {
		return flat_null;
	}

	accessor_last() {
		return flat_null;
	}

	static __assignments() {
	}
	static HashSet_HashSetIterator
}

class HashSet_HashSetIterator extends FlatObject {
	bucketIterator = flat_null;
	valueIterator = flat_null;
	position = 0;
	set = flat_null;

	static construct(set) {
		let __value = new HashSet_HashSetIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		HashSet_HashSetIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = HashSet_HashSetIterator.__init.call(__value, set);

		return __value;
	}

	destroy() {
	}

	static __init(set) {
		let self = this;

		this.set = set;
		this.reset();
		return self;
	}

	reset() {
		this.position = 0;
		this.bucketIterator = this.set.buckets.accessor_iterator();
		this.valueIterator = this.bucketIterator.accessor_hasNext() ? this.bucketIterator.accessor_stepNext().accessor_iterator() : flat_null;
		return this;
	}

	static generated146(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated153(value0, value1, value2, value3, value4, value5, value6, value7) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		return FlatArray.construct1(temp, 8);
	}

	static generated163(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	accessor_stepNext() {
		let flat_local_0 = flat_null;
		if (((flat_local_0 = this.valueIterator) !== flat_null ? (flat_local_0.accessor_hasNext()) : false)) {
			this.position++;
			return this.valueIterator.accessor_stepNext();
		} else if (this.bucketIterator.accessor_hasNext()) {
			this.position++;
			this.valueIterator = this.bucketIterator.accessor_stepNext().accessor_iterator();
			return this.valueIterator.accessor_stepNext();
		}
		throw NoSuchElementException.construct(undefined);
	}

	accessor_previous() {
		let flat_local_0 = flat_null;
		if (((flat_local_0 = this.valueIterator) !== flat_null ? (flat_local_0.accessor_hasPrevious()) : false)) {
			return this.valueIterator.accessor_previous();
		} else if (this.bucketIterator.accessor_hasPrevious()) {
			return this.bucketIterator.accessor_previous().accessor_last();
		} else {
			return flat_null;
		}
	}

	accessor_next() {
		let flat_local_0 = flat_null;
		if (((flat_local_0 = this.valueIterator) !== flat_null ? (flat_local_0.accessor_hasNext()) : false)) {
			return this.valueIterator.accessor_next();
		} else if (this.bucketIterator.accessor_hasNext()) {
			return this.bucketIterator.accessor_next().accessor_first();
		} else {
			return flat_null;
		}
	}

	static accessor__js_class() {
		return typeof HashSet_HashSetIterator.__lazy_accessor__js_class !== 'undefined' ? HashSet_HashSetIterator.__lazy_accessor__js_class : (HashSet_HashSetIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/HashSet.HashSetIterator"), false, FlatObject.accessor__js_class(), HashSet_HashSetIterator.generated146(Iterator.accessor__js_class()), HashSet_HashSetIterator.generated153(Field.construct(FlatString.construct5("position")), Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("hasPrevious")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("current")), Field.construct(FlatString.construct5("previous")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("set"))), HashSet_HashSetIterator.generated163(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("reset"))), this);
			})());
	}

	accessor_hasNext() {
		let flat_local_0 = flat_null;
		return this.position < this.set.accessor_count() && (this.bucketIterator.accessor_hasNext() || ((flat_local_0 = this.valueIterator) !== flat_null ? (flat_local_0.accessor_hasNext()) : false));
	}

	accessor_hasPrevious() {
		let flat_local_0 = flat_null;
		return this.bucketIterator.accessor_hasPrevious() || ((flat_local_0 = this.valueIterator) !== flat_null ? (flat_local_0.accessor_hasPrevious()) : false);
	}

	accessor_current() {
		let flat_local_0 = flat_null;
		return ((flat_local_0 = this.valueIterator) !== flat_null ? (flat_local_0.accessor_current()) : flat_null);
	}

	static __assignments() {
	}
}

class Import extends FlatObject {
	static construct() {
		let __value = new Import();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Import.__assignments.apply(__value, [].slice.call(arguments));
		__value = Import.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static async import(module) {
		return await import(module.chars.data);
		return flat_null;
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated51(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static accessor__js_class() {
		return typeof Import.__lazy_accessor__js_class !== 'undefined' ? Import.__lazy_accessor__js_class : (Import.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/node/import/Import"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), Import.generated51(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("import")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class TestRunner {
	async runTests(onResult, out) {
		onResult = typeof onResult === 'undefined' ? (_1) => {
		} : onResult;
		out = typeof out === 'undefined' ? FlatConsole.out : out;
	}

	static generated189(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated194(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof TestRunner.__lazy_accessor__js_class !== 'undefined' ? TestRunner.__lazy_accessor__js_class : (TestRunner.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/test/TestRunner"), true, FlatObject.accessor__js_class(), FlatArray.construct(), TestRunner.generated189(Field.construct(FlatString.construct5("model")), Field.construct(FlatString.construct5("testCases")), Field.construct(FlatString.construct5("description"))), TestRunner.generated194(Function.construct(FlatString.construct5("runTests"))), this);
			})());
	}

	accessor_model() {
		return flat_null;
	}

	accessor_testCases() {
		return this.accessor_model().accessor_testCases();
	}

	accessor_description() {
		return this.accessor_model().description;
	}
}

class Import_Test extends FlatObject {
	static _can_import_cryptoTestCase = flat_null;
	static _can_use_cryptoTestCase = flat_null;
	static _runTestsTestRunner = flat_null;

	static construct() {
		let __value = new Import_Test();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Import_Test.__assignments.apply(__value, [].slice.call(arguments));
		__value = Import_Test.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	async can_import_crypto(out) {
		out = typeof out === 'undefined' ? FlatConsole.out : out;
		out.write(FlatString.construct5("Testing can import crypto "));
		let crypto = (await Import.import(FlatString.construct5("crypto")));
		Test.expect0(crypto).toNotBe(flat_null, undefined);
	}

	async can_use_crypto(out) {
		let data = flat_null;
		out = typeof out === 'undefined' ? FlatConsole.out : out;
		out.write(FlatString.construct5("Testing can use crypto "));
		let crypto = (await Import.import(FlatString.construct5("crypto")));
		data = crypto.randomUUID();
		Test.expect0(FlatInt.construct(FlatString.construct4(data).count)).toBe(FlatByte.construct(36), undefined);
	}

	async runTests(onResult, out) {
		onResult = typeof onResult === 'undefined' ? (_1) => {
		} : onResult;
		out = typeof out === 'undefined' ? FlatConsole.out : out;
		Test.out.writeHeader1(FlatString.construct5("Testing Import_Test"), undefined, undefined, undefined);
		let can_import_cryptoTimer = Timer.construct().start();
		try {
			(await this.can_import_crypto(out));
			can_import_cryptoTimer.stop();
			out.__chain('write', [FlatString.construct5("- Success ").plus0(FlatLong.toString((can_import_cryptoTimer.accessor_duration())).plus0(FlatString.construct5("ms\n")))]);
			let testResult = TestResult.construct(true, can_import_cryptoTimer, Import_Test._can_import_cryptoTestCase, undefined);
			onResult(testResult);
		} catch (e)  {
			if (!(e instanceof Exception)) {
				console.log(0.4374648572373212);
				console.error(e);
				process.exit(1);
			} else {
				can_import_cryptoTimer.stop();
				out.write(FlatString.construct5("- Failure: ").plus0((e.message).plus0(FlatString.construct5(" ").plus0(FlatLong.toString((can_import_cryptoTimer.accessor_duration())).plus0(FlatString.construct5("ms\n"))))));
				let testResult = TestResult.construct(false, can_import_cryptoTimer, Import_Test._can_import_cryptoTestCase, e.message);
				onResult(testResult);
			}
		 }
		finally {
		}
		let can_use_cryptoTimer = Timer.construct().start();
		try {
			(await this.can_use_crypto(out));
			can_use_cryptoTimer.stop();
			out.__chain('write', [FlatString.construct5("- Success ").plus0(FlatLong.toString((can_use_cryptoTimer.accessor_duration())).plus0(FlatString.construct5("ms\n")))]);
			let testResult = TestResult.construct(true, can_use_cryptoTimer, Import_Test._can_use_cryptoTestCase, undefined);
			onResult(testResult);
		} catch (e)  {
			if (!(e instanceof Exception)) {
				console.log(0.7775283477589645);
				console.error(e);
				process.exit(1);
			} else {
				can_use_cryptoTimer.stop();
				out.write(FlatString.construct5("- Failure: ").plus0((e.message).plus0(FlatString.construct5(" ").plus0(FlatLong.toString((can_use_cryptoTimer.accessor_duration())).plus0(FlatString.construct5("ms\n"))))));
				let testResult = TestResult.construct(false, can_use_cryptoTimer, Import_Test._can_use_cryptoTestCase, e.message);
				onResult(testResult);
			}
		 }
		finally {
		}
		out.write(FlatString.construct5("\n"));
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated54(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated64(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated82(value0, value1, value2, value3, value4) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		return FlatArray.construct1(temp, 5);
	}

	static generated266(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof Import_Test.__lazy_accessor__js_class !== 'undefined' ? Import_Test.__lazy_accessor__js_class : (Import_Test.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/node/import/Import_Test"), false, FlatObject.accessor__js_class(), Import_Test.generated54(TestRunner.accessor__js_class()), Import_Test.generated64(Field.construct(FlatString.construct5("model")), Field.construct(FlatString.construct5("_can_import_cryptoTestCase")), Field.construct(FlatString.construct5("_can_use_cryptoTestCase")), Field.construct(FlatString.construct5("_runTestsTestRunner"))), Import_Test.generated82(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("can_import_crypto")), Function.construct(FlatString.construct5("can_use_crypto")), Function.construct(FlatString.construct5("runTests")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor_model() {
		return Import_Test._runTestsTestRunner;
	}

	static __assignments() {
	}
}

class Import_TestSuite extends FlatObject {
	static _runTestsTestSuite = flat_null;

	static construct() {
		let __value = new Import_TestSuite();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Import_TestSuite.__assignments.apply(__value, [].slice.call(arguments));
		__value = Import_TestSuite.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static async main(args) {
		let self = this;

		let test = Import_TestSuite.construct();
		let results = FlatArray.construct();
		let timer = Timer.construct().start();
		(await test.runTests((_1) => {
					results.add0(_1);
		}, undefined));
		timer.stop();
		let fancy = FancyOutputStream.construct(undefined, undefined, FlatString.construct5("/\\"));
		let unsuccessful = results.filter1((_x, _i, _array) => {
				return !_x.success;
		});
		if (unsuccessful.accessor_count() > 0) {
			fancy.writeHeader1(FlatInt.toString((unsuccessful.accessor_count())).plus0(FlatString.construct5(" Failure").plus0((unsuccessful.accessor_count() === 1 ? FlatString.construct5("") : FlatString.construct5("s")).plus0(FlatString.construct5(" out of ").plus0(FlatInt.toString((results.accessor_count())).plus0(FlatString.construct5(" test").plus0((results.accessor_count() === 1 ? FlatString.construct5("") : FlatString.construct5("s")).plus0(FlatString.construct5("")))))))), FlatString.construct5(":'( "), undefined, true);
			if (unsuccessful.accessor_count() > 1 || results.accessor_last().success) {
				unsuccessful.forEach1((_x, _i, _array) => {
						FlatConsole.log((_x).toString().plus0(FlatString.construct5("")));
				});
			}
		} else {
			fancy.writeHeader1(FlatString.construct5("All ").plus0(FlatInt.toString((results.accessor_count())).plus0(FlatString.construct5(" Successful"))), FlatString.construct5(":) "), undefined, true);
	}
	FlatConsole.writeLine1(FlatString.construct5("Took ").plus0(FlatLong.toString((timer.accessor_duration())).plus0(FlatString.construct5("ms"))));
	if (unsuccessful.accessor_count() > 0) {
		System.exit0(1);
	}
}

async runTests(onResult, out) {
	onResult = typeof onResult === 'undefined' ? (_1) => {
	} : onResult;
	out = typeof out === 'undefined' ? FlatConsole.out : out;
	let testImport_Test = Import_Test.construct();
	(await testImport_Test.runTests(onResult, out));
}

static __init() {
	let self = this;

	return self;
}

static generated52(value0) {
	let temp = flat_null;
	temp = [];
	temp[0] = value0;
	return FlatArray.construct1(temp, 1);
}

static generated61(value0, value1) {
	let temp = flat_null;
	temp = [];
	temp[0] = value0;
	temp[1] = value1;
	return FlatArray.construct1(temp, 2);
}

static generated68(value0, value1, value2, value3) {
	let temp = flat_null;
	temp = [];
	temp[0] = value0;
	temp[1] = value1;
	temp[2] = value2;
	temp[3] = value3;
	return FlatArray.construct1(temp, 4);
}

static generated267(value0) {
	let temp = flat_null;
	temp = [];
	temp[0] = value0;
	return FlatArray.construct1(temp, 1);
}

static generated268(value0) {
	let temp = flat_null;
	temp = [];
	temp[0] = value0;
	return FlatArray.construct1(temp, 1);
}

static accessor__js_class() {
	return typeof Import_TestSuite.__lazy_accessor__js_class !== 'undefined' ? Import_TestSuite.__lazy_accessor__js_class : (Import_TestSuite.__lazy_accessor__js_class = (() => {
				return Class.construct1(FlatString.construct5("flat/node/import/Import_TestSuite"), false, FlatObject.accessor__js_class(), Import_TestSuite.generated52(TestRunner.accessor__js_class()), Import_TestSuite.generated61(Field.construct(FlatString.construct5("model")), Field.construct(FlatString.construct5("_runTestsTestSuite"))), Import_TestSuite.generated68(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("main")), Function.construct(FlatString.construct5("runTests")), Function.construct(FlatString.construct5("this"))), this);
		})());
}

accessor_model() {
	return Import_TestSuite._runTestsTestSuite;
}

static __assignments() {
}}

class FlatInt extends Number {
	value = 0;
	static MAX_VALUE = 0;
	static MIN_VALUE = 0;

	static construct(value) {
		let __value = new FlatInt();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Primitive.__assignments.apply(__value, [].slice.call(arguments));
		Number.__assignments.apply(__value, [].slice.call(arguments));
		FlatInt.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatInt.__init.call(__value, value);

		return __value;
	}

	destroy() {
	}

	static __init(value) {
		let self = this;

		this.value = value;
		return self;
	}

	compareTo0(other) {
		let flat_local_0 = flat_null;
		return this.value - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0);
	}

	compareToReal(other) {
		return this.value - other;
	}

	compareToInteger(other) {
		return this.value - other;
	}

	static compareTo(value, other) {
		return value - other;
	}

	static compareToReal(value, other) {
		return value - other;
	}

	static compareToInteger(value, other) {
		return value - other;
	}

	plus0(other) {
		let flat_local_0 = flat_null;
		return FlatInt.construct(this.value + ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	plusReal(other) {
		return this.value + other;
	}

	plusInteger(other) {
		return this.value + other;
	}

	plusEquals0(other) {
		let flat_local_0 = flat_null;
		return FlatInt.construct(this.value += ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	plusEqualsReal(other) {
		return this.value += other;
	}

	plusEqualsInteger(other) {
		return this.value += other;
	}

	minus(other) {
		let flat_local_0 = flat_null;
		return FlatInt.construct(this.value - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	minusReal(other) {
		return this.value - other;
	}

	minusInteger(other) {
		return this.value - other;
	}

	minusEquals(other) {
		let flat_local_0 = flat_null;
		return FlatInt.construct(this.value -= ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	minusEqualsReal(other) {
		return this.value -= other;
	}

	minusEqualsInteger(other) {
		return this.value -= other;
	}

	multiply0(other) {
		let flat_local_0 = flat_null;
		return FlatInt.construct(this.value + ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	multiplyReal(other) {
		return this.value * other;
	}

	multiplyInteger(other) {
		return this.value * other;
	}

	multiplyEquals(other) {
		let flat_local_0 = flat_null;
		return FlatInt.construct(this.value *= ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	multiplyEqualsReal(other) {
		return this.value *= other;
	}

	multiplyEqualsInteger(other) {
		return this.value *= other;
	}

	equals(other) {
		let flat_local_0 = flat_null;
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(Number.accessor__js_class()) && ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0) === this.value;
	}

	equals0(other) {
		return this.value === other;
	}

	equalsReal(other) {
		return this.value === other;
	}

	equalsInteger(other) {
		return this.value === other;
	}

	static equals(value, other) {
		return value === other;
	}

	static equalsReal(value, other) {
		return value === other;
	}

	static equalsInteger(value, other) {
		return value === other;
	}

	toString() {
		return FlatInt.toString(this.value);
	}

	static numDigits(number) {
		return FlatLong.numDigits(number);
	}

	static toString(value) {
		return FlatLong.toString(value);
	}

	static toJson(value, format, tab) {
		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		return FlatInt.toString(value);
	}

	static hashCodeLong(value) {
		return FlatMath.abs1(value);
	}

	static plus(a, b) {
		return a + b;
	}

	toFileSize() {
		return FlatInt.toFileSize(this.value);
	}

	static toFileSize(value) {
		return FlatLong.toFileSize(value);
	}

	toEnglish() {
		return FlatInt.toEnglish(this.value);
	}

	static toEnglish(value) {
		return FlatLong.toEnglish(value);
	}

	static parseOr(num, _js_default) {
		if (!((num) !== flat_null)) {
			return _js_default;
		}
		if (num.accessor_isEmpty()) {
			return _js_default;
		}
		{
			let i = 0;
			for (; i < num.count; i++) {
				let c = num.get(i);
				if (i === 0 && c === '-') {
					if (num.count > 1) {
						continue;
					}
					return _js_default;
				}
				switch (c) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
					continue;
				}
				return _js_default;
			}
		}
		return FlatInt.parseInt(num);
	}

	static parseInt(num) {
		return parseInt(num.chars.data);
	}

	static generated59(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated70(value0, value1, value2, value3, value4, value5) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		return FlatArray.construct1(temp, 6);
	}

	static generated122(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		temp[41] = value41;
		temp[42] = value42;
		temp[43] = value43;
		temp[44] = value44;
		return FlatArray.construct1(temp, 45);
	}

	static accessor__js_class() {
		return typeof FlatInt.__lazy_accessor__js_class !== 'undefined' ? FlatInt.__lazy_accessor__js_class : (FlatInt.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/primitive/number/Int"), false, Number.accessor__js_class(), FlatInt.generated59(Integer.accessor__js_class()), FlatInt.generated70(Field.construct(FlatString.construct5("hashCodeLong")), Field.construct(FlatString.construct5("realValue")), Field.construct(FlatString.construct5("integerValue")), Field.construct(FlatString.construct5("value")), Field.construct(FlatString.construct5("MAX_VALUE")), Field.construct(FlatString.construct5("MIN_VALUE"))), FlatInt.generated122(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("plusReal")), Function.construct(FlatString.construct5("plusInteger")), Function.construct(FlatString.construct5("plusEquals")), Function.construct(FlatString.construct5("plusEqualsReal")), Function.construct(FlatString.construct5("plusEqualsInteger")), Function.construct(FlatString.construct5("minus")), Function.construct(FlatString.construct5("minusReal")), Function.construct(FlatString.construct5("minusInteger")), Function.construct(FlatString.construct5("minusEquals")), Function.construct(FlatString.construct5("minusEqualsReal")), Function.construct(FlatString.construct5("minusEqualsInteger")), Function.construct(FlatString.construct5("multiply")), Function.construct(FlatString.construct5("multiplyReal")), Function.construct(FlatString.construct5("multiplyInteger")), Function.construct(FlatString.construct5("multiplyEquals")), Function.construct(FlatString.construct5("multiplyEqualsReal")), Function.construct(FlatString.construct5("multiplyEqualsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("numDigits")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("hashCodeLong")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("toFileSize")), Function.construct(FlatString.construct5("toFileSize")), Function.construct(FlatString.construct5("toEnglish")), Function.construct(FlatString.construct5("toEnglish")), Function.construct(FlatString.construct5("parseOr")), Function.construct(FlatString.construct5("parseInt"))), this);
			})());
	}

	accessor_hashCodeLong() {
		return this.value;
	}

	accessor_realValue() {
		return this.value;
	}

	accessor_integerValue() {
		return this.value;
	}

	static __assignments() {
	}
}

class IntRange extends FlatObject {
	start = 0;
	end = 0;
	count = 0;

	static construct() {
		let __value = new IntRange();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		IntRange.__assignments.apply(__value, [].slice.call(arguments));
		__value = IntRange.__init.call(__value);

		return __value;
	}

	static construct0(start, end) {
		let __value = new IntRange();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		IntRange.__assignments.apply(__value, [].slice.call(arguments));
		__value = IntRange.__init0.call(__value, start, end);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		self = IntRange.__init0.call(this, 0, 0);
		return self;
	}

	static __init0(start, end) {
		let self = this;

		this.start = start;
		this.end = end;
		return self;
	}

	contains0(value) {
		let flat_local_0 = flat_null;
		let flat_local_1 = flat_null;
		return ((flat_local_0 = (value)) !== flat_null ? (flat_local_0.value) : 0) >= this.start && ((flat_local_1 = (value)) !== flat_null ? (flat_local_1.value) : 0) < this.end;
	}

	toArray() {
		let ints = FlatArray.construct0(this.accessor_size(), undefined);
		let i = this.start;
		while (i < this.end) {
			ints.set(i - this.start, FlatInt.construct(i++));
		}
		return ints;
	}

	reverse() {
		return IntRange.construct0(this.end, this.start);
	}

	toString() {
		return FlatInt.toString((this.start)).plus0(FlatString.construct5("..").plus0(FlatInt.toString((this.end)).plus0(FlatString.construct5(""))));
	}

	static generated60(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated71(value0, value1, value2, value3, value4, value5) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		return FlatArray.construct1(temp, 6);
	}

	static generated93(value0, value1, value2, value3, value4, value5, value6, value7) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		return FlatArray.construct1(temp, 8);
	}

	mutator_last(value) {
		let flat_local_0 = flat_null;
		this.end = ((flat_local_0 = (value)) !== flat_null ? (flat_local_0.value) : 0) + 1;
		return value;
	}

	static accessor__js_class() {
		return typeof IntRange.__lazy_accessor__js_class !== 'undefined' ? IntRange.__lazy_accessor__js_class : (IntRange.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/IntRange"), false, FlatObject.accessor__js_class(), IntRange.generated60(List.accessor__js_class()), IntRange.generated71(Field.construct(FlatString.construct5("size")), Field.construct(FlatString.construct5("iterator")), Field.construct(FlatString.construct5("first")), Field.construct(FlatString.construct5("last")), Field.construct(FlatString.construct5("start")), Field.construct(FlatString.construct5("end"))), IntRange.generated93(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("contains")), Function.construct(FlatString.construct5("toArray")), Function.construct(FlatString.construct5("reverse")), Function.construct(FlatString.construct5("toString"))), this);
			})());
	}

	accessor_size() {
		return this.end - this.start;
	}

	accessor_iterator() {
		return IntRangeIterator.construct(this);
	}

	accessor_first() {
		return FlatInt.construct(this.start);
	}

	mutator_first(value) {
		let flat_local_0 = flat_null;
		this.start = ((flat_local_0 = (value)) !== flat_null ? (flat_local_0.value) : 0);
		return value;
	}

	accessor_last() {
		return FlatInt.construct(this.end - 1);
	}

	accessor_count() {
		return this.count;
	}

	mutator_count(value) {
		this.count = value;
		return value;
	}

	static __assignments() {
	}
}

class IntRangeIterator extends FlatObject {
	range = flat_null;
	position = 0;

	static construct(range) {
		let __value = new IntRangeIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		IntRangeIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = IntRangeIterator.__init.call(__value, range);

		return __value;
	}

	destroy() {
	}

	static __init(range) {
		let self = this;

		this.range = range;
		this.reset();
		return self;
	}

	reset() {
		this.position = this.range.start;
		return this;
	}

	static generated63(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated77(value0, value1, value2, value3, value4, value5, value6) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		return FlatArray.construct1(temp, 7);
	}

	static generated94(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	accessor_stepNext() {
		if (this.accessor_hasNext()) {
			return FlatInt.construct(this.position++);
		}
		throw NoSuchElementException.construct(undefined);
	}

	static accessor__js_class() {
		return typeof IntRangeIterator.__lazy_accessor__js_class !== 'undefined' ? IntRangeIterator.__lazy_accessor__js_class : (IntRangeIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/IntRangeIterator"), false, FlatObject.accessor__js_class(), IntRangeIterator.generated63(Iterator.accessor__js_class()), IntRangeIterator.generated77(Field.construct(FlatString.construct5("position")), Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("hasPrevious")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("current")), Field.construct(FlatString.construct5("previous")), Field.construct(FlatString.construct5("next"))), IntRangeIterator.generated94(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("reset"))), this);
			})());
	}

	accessor_hasNext() {
		return this.position < this.range.end;
	}

	accessor_hasPrevious() {
		return this.position > this.range.start;
	}

	accessor_current() {
		return FlatInt.construct(this.position);
	}

	accessor_previous() {
		return FlatInt.construct(this.position - 1);
	}

	accessor_next() {
		return FlatInt.construct(this.position + 1);
	}

	static __assignments() {
	}
}

class InvalidArgumentException extends Exception {
	static construct(message) {
		let __value = new InvalidArgumentException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		InvalidArgumentException.__assignments.apply(__value, [].slice.call(arguments));
		__value = InvalidArgumentException.__init.call(__value, message);

		return __value;
	}

	destroy() {
	}

	static __init(message) {
		let self = this;

		self = Exception.__init.call(this, message);
		return self;
	}

	static generated66(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof InvalidArgumentException.__lazy_accessor__js_class !== 'undefined' ? InvalidArgumentException.__lazy_accessor__js_class : (InvalidArgumentException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/exception/InvalidArgumentException"), false, Exception.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), InvalidArgumentException.generated66(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class TestException extends Exception {
	static construct(message) {
		let __value = new TestException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		TestException.__assignments.apply(__value, [].slice.call(arguments));
		__value = TestException.__init.call(__value, message);

		return __value;
	}

	destroy() {
	}

	static __init(message) {
		let self = this;

		message = typeof message === 'undefined' ? flat_null : message;
		self = Exception.__init.call(this, message);
		return self;
	}

	static generated176(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof TestException.__lazy_accessor__js_class !== 'undefined' ? TestException.__lazy_accessor__js_class : (TestException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/test/TestException"), false, Exception.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), TestException.generated176(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class InvalidAssertionException extends TestException {
	static construct(message) {
		let __value = new InvalidAssertionException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		TestException.__assignments.apply(__value, [].slice.call(arguments));
		InvalidAssertionException.__assignments.apply(__value, [].slice.call(arguments));
		__value = InvalidAssertionException.__init.call(__value, message);

		return __value;
	}

	destroy() {
	}

	static __init(message) {
		let self = this;

		message = typeof message === 'undefined' ? flat_null : message;
		self = TestException.__init.call(this, message);
		return self;
	}

	static generated67(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof InvalidAssertionException.__lazy_accessor__js_class !== 'undefined' ? InvalidAssertionException.__lazy_accessor__js_class : (InvalidAssertionException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/test/InvalidAssertionException"), false, TestException.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), InvalidAssertionException.generated67(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class InvalidOperationException extends Exception {
	static construct(message) {
		let __value = new InvalidOperationException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		InvalidOperationException.__assignments.apply(__value, [].slice.call(arguments));
		__value = InvalidOperationException.__init.call(__value, message);

		return __value;
	}

	destroy() {
	}

	static __init(message) {
		let self = this;

		self = Exception.__init.call(this, message);
		return self;
	}

	static generated72(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof InvalidOperationException.__lazy_accessor__js_class !== 'undefined' ? InvalidOperationException.__lazy_accessor__js_class : (InvalidOperationException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/exception/InvalidOperationException"), false, Exception.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), InvalidOperationException.generated72(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class Iterable {
	static generated69(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	accessor_iterator() {
		return flat_null;
	}

	mutator_iterator(value) {
		return value;
	}

	static accessor__js_class() {
		return typeof Iterable.__lazy_accessor__js_class !== 'undefined' ? Iterable.__lazy_accessor__js_class : (Iterable.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/Iterable"), true, FlatObject.accessor__js_class(), FlatArray.construct(), Iterable.generated69(Field.construct(FlatString.construct5("iterator"))), FlatArray.construct(), this);
			})());
	}
}

class LinkedList extends FlatObject {
	start = flat_null;
	current = flat_null;
	size = 0;
	count = 0;

	static construct() {
		let __value = new LinkedList();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		LinkedList.__assignments.apply(__value, [].slice.call(arguments));
		__value = LinkedList.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	addAll(data) {
		let flat_local_0 = flat_null;
		flat_local_0 = (data).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let d = flat_null;
			d = flat_local_0.accessor_stepNext();
			this.add(d);
		}
		return this;
	}

	add(data) {
		let node = ListNode.construct(data, undefined);
		if ((this.start) !== flat_null) {
			this.current.next = node;
		} else {
			this.start = node;
			this.current = node;
		}
		this.current = node;
		this.size++;
		return this;
	}

	remove(data) {
		if (this.start.data.equals(data)) {
			this.start = this.start.next;
		}
		let prev = this.start;
		let cur = this.start.next;
		while ((cur) !== flat_null) {
			let d = cur.data;
			if (d.equals(data)) {
				prev.next = cur.next;
				this.size--;
			}
			cur = cur.next;
		}
		return this;
	}

	contains0(value) {
		let self = this;

		return this.any0((_x, _i, _list) => {
				return _x.equals(value);
		});
	}

	toArray() {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct0(this.size, undefined);
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			array.set(i++, element);
		}
		return array;
	}

	map1(mapFunc) {
		let flat_local_0 = flat_null;
		let array = LinkedList.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			array.add(mapFunc(element, i++, this));
		}
		return array;
	}

	forEach1(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			func(element, i++, this);
		}
		return this;
	}

	any0(anyFunc) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (anyFunc(element, i++, this)) {
				return true;
			}
		}
		return false;
	}

	all0(allFunc, stopOnContradiction) {
		let flat_local_0 = flat_null;
		stopOnContradiction = typeof stopOnContradiction === 'undefined' ? true : stopOnContradiction;
		let contradiction = false;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (!allFunc(element, i++, this)) {
				if (stopOnContradiction) {
					return false;
				}
				contradiction = true;
			}
		}
		return !contradiction;
	}

	filter1(filterFunc) {
		let flat_local_0 = flat_null;
		let filtered = LinkedList.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (filterFunc(element, i++, this)) {
				filtered.add(element);
			}
		}
		return filtered;
	}

	take(howMany) {
		let flat_local_0 = flat_null;
		if (howMany > this.size) {
			howMany = this.size;
		}
		let list = LinkedList.construct();
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (list.size === howMany) {
				break;
			}
			list.add(element);
		}
		return list;
	}

	skip(howMany) {
		let flat_local_0 = flat_null;
		let list = LinkedList.construct();
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (i++ > howMany) {
				list.add(element);
			}
		}
		return list;
	}

	firstWhere0(func) {
		let flat_local_0 = flat_null;
		let i = 0;
		flat_local_0 = (this).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let element = flat_null;
			element = flat_local_0.accessor_stepNext();
			if (func(element, i++, this)) {
				return element;
			}
		}
		return flat_null;
	}

	reverse() {
		let list = LinkedList.construct();
		let prev = flat_null;
		let current = flat_null;
		let next = flat_null;
		if ((this.start) !== flat_null) {
			current = this.start.clone();
		}
		while ((current) !== flat_null) {
			next = current.next;
			current.next = flat_null;
			if ((next) !== flat_null) {
				next = next.clone();
			}
			if ((prev) !== flat_null) {
				current.next = prev.clone();
			}
			prev = current;
			current = next;
		}
		list.start = prev;
		return list;
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated78(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated92(value0, value1, value2, value3, value4, value5) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		return FlatArray.construct1(temp, 6);
	}

	static generated124(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		return FlatArray.construct1(temp, 16);
	}

	mutator_first(value) {
		this.start = ListNode.construct(value, this.start);
		return value;
	}

	mutator_last(value) {
		this.current.next = ListNode.construct(value, undefined);
		this.current = this.current.next;
		return value;
	}

	static accessor__js_class() {
		return typeof LinkedList.__lazy_accessor__js_class !== 'undefined' ? LinkedList.__lazy_accessor__js_class : (LinkedList.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/LinkedList"), false, FlatObject.accessor__js_class(), LinkedList.generated78(List.accessor__js_class()), LinkedList.generated92(Field.construct(FlatString.construct5("start")), Field.construct(FlatString.construct5("current")), Field.construct(FlatString.construct5("size")), Field.construct(FlatString.construct5("iterator")), Field.construct(FlatString.construct5("first")), Field.construct(FlatString.construct5("last"))), LinkedList.generated124(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("addAll")), Function.construct(FlatString.construct5("add")), Function.construct(FlatString.construct5("remove")), Function.construct(FlatString.construct5("contains")), Function.construct(FlatString.construct5("toArray")), Function.construct(FlatString.construct5("map")), Function.construct(FlatString.construct5("forEach")), Function.construct(FlatString.construct5("any")), Function.construct(FlatString.construct5("all")), Function.construct(FlatString.construct5("filter")), Function.construct(FlatString.construct5("take")), Function.construct(FlatString.construct5("skip")), Function.construct(FlatString.construct5("firstWhere")), Function.construct(FlatString.construct5("reverse")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor_iterator() {
		return LinkedListIterator.construct(this);
	}

	accessor_first() {
		let flat_local_0 = flat_null;
		return ((flat_local_0 = this.start) !== flat_null ? (flat_local_0.data) : flat_null);
	}

	accessor_last() {
		let flat_local_0 = flat_null;
		return ((flat_local_0 = this.current) !== flat_null ? (flat_local_0.data) : flat_null);
	}

	accessor_count() {
		return this.count;
	}

	mutator_count(value) {
		this.count = value;
		return value;
	}

	static __assignments() {
	}
}

class LinkedListIterator extends FlatObject {
	list = flat_null;
	position = flat_null;
	previousNode = flat_null;

	static construct(list) {
		let __value = new LinkedListIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		LinkedListIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = LinkedListIterator.__init.call(__value, list);

		return __value;
	}

	destroy() {
	}

	static __init(list) {
		let self = this;

		this.list = list;
		this.reset();
		return self;
	}

	reset() {
		this.position = this.list.start;
		this.previousNode = flat_null;
		return this;
	}

	static generated79(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated83(value0, value1, value2, value3, value4, value5, value6, value7) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		return FlatArray.construct1(temp, 8);
	}

	static generated165(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	accessor_stepNext() {
		if (this.accessor_hasNext()) {
			let data = this.position.data;
			this.previousNode = this.position;
			this.position = this.position.next;
			return data;
		}
		throw NoSuchElementException.construct(undefined);
	}

	static accessor__js_class() {
		return typeof LinkedListIterator.__lazy_accessor__js_class !== 'undefined' ? LinkedListIterator.__lazy_accessor__js_class : (LinkedListIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/LinkedListIterator"), false, FlatObject.accessor__js_class(), LinkedListIterator.generated79(Iterator.accessor__js_class()), LinkedListIterator.generated83(Field.construct(FlatString.construct5("position")), Field.construct(FlatString.construct5("previousNode")), Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("hasPrevious")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("current")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("previous"))), LinkedListIterator.generated165(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("reset"))), this);
			})());
	}

	accessor_hasNext() {
		return this.position !== flat_null;
	}

	accessor_hasPrevious() {
		return this.previousNode !== flat_null;
	}

	accessor_current() {
		let flat_local_0 = flat_null;
		return ((flat_local_0 = this.position) !== flat_null ? (flat_local_0.data) : flat_null);
	}

	accessor_next() {
		let flat_local_0 = flat_null;
		let flat_local_1 = flat_null;
		return ((flat_local_0 = this.position) !== flat_null ? (((flat_local_1 = flat_local_0.next) !== flat_null ? (flat_local_1.data) : flat_null)) : flat_null);
	}

	accessor_previous() {
		let flat_local_0 = flat_null;
		return ((flat_local_0 = this.previousNode) !== flat_null ? (flat_local_0.data) : flat_null);
	}

	static __assignments() {
	}
}

class ListNode extends FlatObject {
	data = flat_null;
	next = flat_null;

	static construct(data, next) {
		let __value = new ListNode();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		ListNode.__assignments.apply(__value, [].slice.call(arguments));
		__value = ListNode.__init.call(__value, data, next);

		return __value;
	}

	destroy() {
	}

	static __init(data, next) {
		let self = this;

		next = typeof next === 'undefined' ? flat_null : next;
		this.data = data;
		this.next = next;
		return self;
	}

	clone() {
		return ListNode.construct(this.data, this.next);
	}

	static generated116(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated126(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static accessor__js_class() {
		return typeof ListNode.__lazy_accessor__js_class !== 'undefined' ? ListNode.__lazy_accessor__js_class : (ListNode.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/ListNode"), false, FlatObject.accessor__js_class(), FlatArray.construct(), ListNode.generated116(Field.construct(FlatString.construct5("data")), Field.construct(FlatString.construct5("next"))), ListNode.generated126(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("clone"))), this);
			})());
	}

	static __assignments() {
	}
}

class Logger extends FlatObject {
	_loggingLevel = flat_null;
	_showPrefix = flat_null;
	_showFormatting = flat_null;
	_lineLength = flat_null;
	_linePrefix = flat_null;
	_lineSuffix = flat_null;
	_prefix = flat_null;
	_suffix = flat_null;
	_prefixSeparator = flat_null;
	_splitOnWord = flat_null;
	filters = flat_null;
	lastDisabledLoggersModificationId = 0;
	_level = flat_null;
	loggerClass = flat_null;
	label = flat_null;
	traceStreams = flat_null;
	debugStreams = flat_null;
	infoStreams = flat_null;
	warnStreams = flat_null;
	errorStreams = flat_null;
	fatalStreams = flat_null;
	static _defaultLoggingLevel = 0;
	static currentDisabledLoggersModificationId = 0;
	static classLoggingLevels = flat_null;
	static labelLoggingLevels = flat_null;
	static wildcardLabelLoggingLevels = flat_null;
	static TRACE = 0;
	static DEBUG = 0;
	static INFO = 0;
	static WARN = 0;
	static ERROR = 0;
	static FATAL = 0;
	static OFF = 0;
	static defaultShowPrefix = flat_null;
	static defaultShowFormatting = flat_null;
	static defaultLineLength = flat_null;
	static defaultSplitOnWord = flat_null;
	static defaultLinePrefix = flat_null;
	static defaultLineSuffix = flat_null;
	static defaultPrefix = flat_null;
	static defaultSuffix = flat_null;
	static defaultPrefixSeparator = flat_null;
	static forceLoggingLevel = flat_null;
	static forceShowPrefix = flat_null;
	static forceShowFormatting = flat_null;
	static forceLineLength = flat_null;
	static forceSplitOnWord = flat_null;
	static forceLinePrefix = flat_null;
	static forceLineSuffix = flat_null;
	static forcePrefix = flat_null;
	static forceSuffix = flat_null;
	static forcePrefixSeparator = flat_null;
	static defaultTraceStreams = flat_null;
	static defaultDebugStreams = flat_null;
	static defaultInfoStreams = flat_null;
	static defaultWarnStreams = flat_null;
	static defaultErrorStreams = flat_null;
	static defaultFatalStreams = flat_null;

	static construct(loggerClass, label, loggingLevel, showPrefix, showFormatting, lineLength, splitOnWord, stream, streams, traceStreams, debugStreams, infoStreams, warnStreams, errorStreams, fatalStreams) {
		let __value = new Logger();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Logger.__assignments.apply(__value, [].slice.call(arguments));
		__value = Logger.__init.call(__value, loggerClass, label, loggingLevel, showPrefix, showFormatting, lineLength, splitOnWord, stream, streams, traceStreams, debugStreams, infoStreams, warnStreams, errorStreams, fatalStreams);

		return __value;
	}

	destroy() {
	}

	static __init(loggerClass, label, loggingLevel, showPrefix, showFormatting, lineLength, splitOnWord, stream, streams, traceStreams, debugStreams, infoStreams, warnStreams, errorStreams, fatalStreams) {
		let self = this;

		let flat_local_0 = flat_null;
		let flat_local_1 = flat_null;
		let flat_local_2 = flat_null;
		let flat_local_3 = flat_null;
		let flat_local_4 = flat_null;
		let flat_local_5 = flat_null;
		loggerClass = typeof loggerClass === 'undefined' ? flat_null : loggerClass;
		label = typeof label === 'undefined' ? flat_null : label;
		loggingLevel = typeof loggingLevel === 'undefined' ? flat_null : loggingLevel;
		showPrefix = typeof showPrefix === 'undefined' ? flat_null : showPrefix;
		showFormatting = typeof showFormatting === 'undefined' ? flat_null : showFormatting;
		lineLength = typeof lineLength === 'undefined' ? flat_null : lineLength;
		splitOnWord = typeof splitOnWord === 'undefined' ? flat_null : splitOnWord;
		stream = typeof stream === 'undefined' ? flat_null : stream;
		streams = typeof streams === 'undefined' ? flat_null : streams;
		traceStreams = typeof traceStreams === 'undefined' ? flat_null : traceStreams;
		debugStreams = typeof debugStreams === 'undefined' ? flat_null : debugStreams;
		infoStreams = typeof infoStreams === 'undefined' ? flat_null : infoStreams;
		warnStreams = typeof warnStreams === 'undefined' ? flat_null : warnStreams;
		errorStreams = typeof errorStreams === 'undefined' ? flat_null : errorStreams;
		fatalStreams = typeof fatalStreams === 'undefined' ? flat_null : fatalStreams;
		this.loggerClass = loggerClass;
		this.label = label;
		this.traceStreams = traceStreams;
		this.debugStreams = debugStreams;
		this.infoStreams = infoStreams;
		this.warnStreams = warnStreams;
		this.errorStreams = errorStreams;
		this.fatalStreams = fatalStreams;
		if ((stream) !== flat_null) {
			this.traceStreams = Logger.generated282(stream);
			this.debugStreams = Logger.generated283(stream);
			this.infoStreams = Logger.generated284(stream);
			this.warnStreams = Logger.generated285(stream);
			this.errorStreams = Logger.generated286(stream);
			this.fatalStreams = Logger.generated287(stream);
		} else if ((streams) !== flat_null) {
			this.traceStreams = streams;
			this.debugStreams = streams;
			this.infoStreams = streams;
			this.warnStreams = streams;
			this.errorStreams = streams;
			this.fatalStreams = streams;
		} else {
			this.traceStreams = (flat_local_0 = traceStreams) !== flat_null ? flat_local_0 : Logger.defaultTraceStreams;
			this.debugStreams = (flat_local_1 = debugStreams) !== flat_null ? flat_local_1 : Logger.defaultDebugStreams;
			this.infoStreams = (flat_local_2 = infoStreams) !== flat_null ? flat_local_2 : Logger.defaultInfoStreams;
			this.warnStreams = (flat_local_3 = warnStreams) !== flat_null ? flat_local_3 : Logger.defaultWarnStreams;
			this.errorStreams = (flat_local_4 = errorStreams) !== flat_null ? flat_local_4 : Logger.defaultErrorStreams;
			this.fatalStreams = (flat_local_5 = fatalStreams) !== flat_null ? flat_local_5 : Logger.defaultFatalStreams;
		}
		this._loggingLevel = loggingLevel;
		this._showPrefix = showPrefix;
		this._showFormatting = showFormatting;
		this._lineLength = lineLength;
		this._splitOnWord = splitOnWord;
		return self;
	}

	static resetLoggingLevels() {
		Logger.classLoggingLevels = HashMap.construct0(undefined, undefined);
		Logger.labelLoggingLevels = HashMap.construct0(undefined, undefined);
		Logger.wildcardLabelLoggingLevels = HashMap.construct0(undefined, undefined);
	}

	static disableLogs0(c) {
		Logger.setLoggingLevel0(c, Logger.OFF);
	}

	static setLoggingLevel0(c, level) {
		Logger.classLoggingLevels.set(c, FlatInt.construct(level));
		Logger.currentDisabledLoggersModificationId++;
	}

	static enableLogs0(c) {
		Logger.classLoggingLevels.remove(c);
		Logger.currentDisabledLoggersModificationId++;
	}

	static disableLogs1(label) {
		Logger.setLoggingLevel1(label, Logger.OFF);
	}

	static setLoggingLevel1(label, level) {
		if (label.contains0('*')) {
			Logger.wildcardLabelLoggingLevels.set(label, FlatInt.construct(level));
		} else {
			Logger.labelLoggingLevels.set(label, FlatInt.construct(level));
		}
		Logger.currentDisabledLoggersModificationId++;
	}

	static enableLogs1(label) {
		if (label.contains0('*')) {
			Logger.wildcardLabelLoggingLevels.remove(label);
		} else {
			Logger.labelLoggingLevels.remove(label);
		}
		Logger.currentDisabledLoggersModificationId++;
	}

	static isClassDisabled(c) {
		return Logger.getClassLoggingLevel(c) === Logger.OFF;
	}

	static isLabelDisabled(label) {
		return Logger.getLabelLoggingLevel(label) === Logger.OFF;
	}

	static getClassLoggingLevel(c) {
		let flat_local_0 = flat_null;
		if (!((c) !== flat_null)) {
			return Logger.accessor_defaultLoggingLevel();
		}
		if (Logger.classLoggingLevels.containsKey(c)) {
			return ((flat_local_0 = (Logger.classLoggingLevels.get(c))) !== flat_null ? (flat_local_0.value) : 0);
		}
		return Logger.getLabelLoggingLevel(c.location);
	}

	static getLabelLoggingLevel(label) {
		let self = this;

		let flat_local_0 = flat_null;
		let flat_local_1 = flat_null;
		if (!((label) !== flat_null)) {
			return Logger.accessor_defaultLoggingLevel();
		}
		if (Logger.labelLoggingLevels.containsKey(label)) {
			return ((flat_local_0 = (Logger.labelLoggingLevels.get(label))) !== flat_null ? (flat_local_0.value) : 0);
		}
		if (Logger.wildcardLabelLoggingLevels.any0((_x, _i, _map) => {
					return WildcardHelper.isWildcardMatch(label, _x.key);
		})) {
			return ((flat_local_1 = (Logger.wildcardLabelLoggingLevels.firstWhere0((_x, _i, _map) => {
								return WildcardHelper.isWildcardMatch(label, _x.key);
				}).value)) !== flat_null ? (flat_local_1.value) : 0);
		}
		return Logger.accessor_defaultLoggingLevel();
	}

	static getLevelFromString(str) {
		if ((str).equals(FlatString.construct5("TRACE"))) {
			return Logger.TRACE;
		} else if ((str).equals(FlatString.construct5("DEBUG"))) {
			return Logger.DEBUG;
		} else if ((str).equals(FlatString.construct5("INFO"))) {
			return Logger.INFO;
		} else if ((str).equals(FlatString.construct5("WARN"))) {
			return Logger.WARN;
		} else if ((str).equals(FlatString.construct5("ERROR"))) {
			return Logger.ERROR;
		} else if ((str).equals(FlatString.construct5("FATAL"))) {
			return Logger.FATAL;
		} else {
			return Logger.accessor_defaultLoggingLevel();
		}

	}

	getPrefix(level) {
		let levelString = flat_null;
		let flat_local_0 = flat_null;
		let flat_local_1 = flat_null;
		if (!((flat_local_1 = this.accessor_showPrefix()) !== flat_null && flat_local_1.value)) {
			return FlatString.construct5("");
		}
		if ((level) === Logger.TRACE) {
			levelString = FlatString.construct5("TRACE");
		} else if ((level) === Logger.DEBUG) {
			levelString = FlatString.construct5("DEBUG");
		} else if ((level) === Logger.INFO) {
			levelString = FlatString.construct5("INFO");
		} else if ((level) === Logger.WARN) {
			levelString = FlatString.construct5("WARN");
		} else if ((level) === Logger.ERROR) {
			levelString = FlatString.construct5("ERROR");
		} else if ((level) === Logger.FATAL) {
			levelString = FlatString.construct5("FATAL");
		}

		let timestamp = FlatDateTime.now().toIsoString();
		let classPrefix = (this.loggerClass) !== flat_null ? this.loggerClass.location.plus0(FlatString.construct5(" ")) : FlatString.construct5("");
		let labelPrefix = (this.label) !== flat_null ? this.label.plus0(FlatString.construct5(" ")) : FlatString.construct5("");
		let suffix = FlatString.construct5(" ").plus0((classPrefix).plus0(FlatString.construct5("").plus0((labelPrefix).plus0(FlatString.construct5(""))))).trimEnd1(undefined, undefined);
		let content = FlatString.construct5("[").plus0((timestamp).plus0(FlatString.construct5("] [").plus0((levelString).plus0(FlatString.construct5("]"))))).plus0(suffix);
		if (((flat_local_0 = this.accessor_showFormatting()) !== flat_null && flat_local_0.value)) {
			let colored = flat_null;
			if ((level) === Logger.TRACE) {
				colored = Colorizer.cyanBackground(content);
			} else if ((level) === Logger.DEBUG) {
				colored = Colorizer.greenBackground(content);
			} else if ((level) === Logger.INFO) {
				colored = Colorizer.grayBackground(content);
			} else if ((level) === Logger.WARN) {
				colored = Colorizer.yellowBackground(content);
			} else if ((level) === Logger.ERROR) {
				colored = Colorizer.redBackground(content);
			} else if ((level) === Logger.FATAL) {
				colored = Colorizer.redBackground(content);
			}

			return colored.plus0(this.accessor_prefixSeparator());
		}
		return content.plus0(this.accessor_prefixSeparator());
	}

	addFilter(filter) {
		this.filters.add0(filter);
	}

	formatContent(content, level) {
		let self = this;

		let formatted = flat_null;
		let flat_local_0 = flat_null;
		this.filters.forEach1((_x, _i, _array) => {
				content = _x(content);
		});
		if (!((flat_local_0 = this.accessor_showFormatting()) !== flat_null && flat_local_0.value)) {
			return content;
		}
		if (level === Logger.TRACE) {
			formatted = Colorizer.cyan(content);
		} else if (level === Logger.DEBUG) {
			formatted = Colorizer.green(content);
		} else if (level === Logger.INFO) {
			formatted = content;
		} else if (level === Logger.WARN) {
			formatted = Colorizer.yellow(content);
		} else if (level === Logger.ERROR) {
			formatted = Colorizer.red(content);
		} else if (level === Logger.FATAL) {
			formatted = Colorizer.red(content);
		}

		return formatted;
	}

	wrapLine(content, prefix) {
		let self = this;

		let flat_local_0 = flat_null;
		prefix = typeof prefix === 'undefined' ? FlatString.construct5("") : prefix;
		if (((flat_local_0 = (this.accessor_lineLength())) !== flat_null ? (flat_local_0.value) : 0) > 0) {
			let lines = content.__callExtension(RegexStringExtensions.String_split, [Pattern.construct(FlatString.construct5("\\n"))]);
			return lines.__callExtension(StreamListExtensions.List_stream, []).flatMap((_x) => {
					let flat_local_1 = flat_null;
					return EscapeCode.wrapLine(_x, ((flat_local_1 = (self.accessor_lineLength())) !== flat_null ? (flat_local_1.value) : 0), prefix, false).__callExtension(StreamListExtensions.List_stream, []);
			}).join(FlatString.construct5("\n"), undefined, undefined, undefined);
		}
		return content;
	}

	addLinePrefix(content) {
		if ((this.accessor_linePrefix()) !== flat_null) {
			let data = flat_null;
			let str = flat_null;
			data = this.accessor_linePrefix().chars.data + content.chars.data.replaceAll("\n", "\n" + this.accessor_linePrefix().chars.data);
			return FlatString.construct4(data);
		}
		return content;
	}

	addLineSuffix(content) {
		if ((this.accessor_lineSuffix()) !== flat_null) {
			let data = flat_null;
			let str = flat_null;
			data = content.chars.data.replaceAll("\n", this.accessor_linePrefix().chars.data + "\n") + this.accessor_lineSuffix().chars.data;
			return FlatString.construct4(data);
		}
		return content;
	}

	addPrefix(content) {
		if ((this.accessor_prefix()) !== flat_null) {
			return this.accessor_prefix().plus0(content);
		}
		return content;
	}

	addSuffix(content) {
		if ((this.accessor_suffix()) !== flat_null) {
			return content.plus0(this.accessor_suffix());
		}
		return content;
	}

	formatMessage(message) {
		return this.wrapLine(this.addSuffix(this.addPrefix(this.addLineSuffix(this.addLinePrefix(message)))), this.addLinePrefix(FlatString.construct5("")));
	}

	static increaseTab(tabCount, tab) {
		tabCount = typeof tabCount === 'undefined' ? 1 : tabCount;
		tab = typeof tab === 'undefined' ? FlatString.construct5("    ") : tab;
		if (!((Logger.defaultLinePrefix) !== flat_null)) {
			Logger.defaultLinePrefix = FlatString.construct5("");
		}
		Logger.defaultLinePrefix = Logger.defaultLinePrefix.concat(tab.repeat(tabCount));
	}

	static decreaseTab(tabCount, tab) {
		tabCount = typeof tabCount === 'undefined' ? 1 : tabCount;
		tab = typeof tab === 'undefined' ? FlatString.construct5("    ") : tab;
		if (!((Logger.defaultLinePrefix) !== flat_null)) {
			return;
		}
		Logger.defaultLinePrefix = Logger.defaultLinePrefix.trimEnd0(tab.count * tabCount);
		if (Logger.defaultLinePrefix.count === 0) {
			Logger.defaultLinePrefix = flat_null;
		}
	}

	static indent(func, tabCount, tab) {
		tabCount = typeof tabCount === 'undefined' ? 1 : tabCount;
		tab = typeof tab === 'undefined' ? FlatString.construct5("    ") : tab;
		Logger.increaseTab(tabCount, tab);
		let value = func();
		Logger.decreaseTab(tabCount, tab);
		return value;
	}

	writeMessage(streams, message, newLine) {
		let self = this;

		if (newLine) {
			streams.forEach1((_x, _i, _array) => {
					_x.writeLine(message);
			});
		} else {
			streams.forEach1((_x, _i, _array) => {
					_x.write(message);
			});
		}
	}

	trace(value, newLine) {
		let flat_local_0 = flat_null;
		newLine = typeof newLine === 'undefined' ? true : newLine;
		if (((flat_local_0 = (this.accessor_loggingLevel())) !== flat_null ? (flat_local_0.value) : 0) < Logger.TRACE) {
			return;
		}
		this.writeMessage(this.traceStreams, this.formatMessage(this.getPrefix(Logger.TRACE).plus0(this.formatContent(value().toString(), Logger.TRACE))), newLine);
	}

	debug(value, newLine) {
		let flat_local_0 = flat_null;
		newLine = typeof newLine === 'undefined' ? true : newLine;
		if (((flat_local_0 = (this.accessor_loggingLevel())) !== flat_null ? (flat_local_0.value) : 0) < Logger.DEBUG) {
			return;
		}
		this.writeMessage(this.debugStreams, this.formatMessage(this.getPrefix(Logger.DEBUG).plus0(this.formatContent(value().toString(), Logger.DEBUG))), newLine);
	}

	info(value, newLine) {
		let flat_local_0 = flat_null;
		newLine = typeof newLine === 'undefined' ? true : newLine;
		if (((flat_local_0 = (this.accessor_loggingLevel())) !== flat_null ? (flat_local_0.value) : 0) < Logger.INFO) {
			return;
		}
		this.writeMessage(this.infoStreams, this.formatMessage(this.getPrefix(Logger.INFO).plus0(this.formatContent(value().toString(), Logger.INFO))), newLine);
	}

	warn(value, newLine) {
		let flat_local_0 = flat_null;
		newLine = typeof newLine === 'undefined' ? true : newLine;
		if (((flat_local_0 = (this.accessor_loggingLevel())) !== flat_null ? (flat_local_0.value) : 0) < Logger.WARN) {
			return;
		}
		this.writeMessage(this.warnStreams, this.formatMessage(this.getPrefix(Logger.WARN).plus0(this.formatContent(value().toString(), Logger.WARN))), newLine);
	}

	error(value, newLine) {
		let flat_local_0 = flat_null;
		newLine = typeof newLine === 'undefined' ? true : newLine;
		if (((flat_local_0 = (this.accessor_loggingLevel())) !== flat_null ? (flat_local_0.value) : 0) < Logger.ERROR) {
			return;
		}
		this.writeMessage(this.errorStreams, this.formatMessage(this.getPrefix(Logger.ERROR).plus0(this.formatContent(value().toString(), Logger.ERROR))), newLine);
	}

	fatal(value, newLine) {
		let flat_local_0 = flat_null;
		newLine = typeof newLine === 'undefined' ? true : newLine;
		if (((flat_local_0 = (this.accessor_loggingLevel())) !== flat_null ? (flat_local_0.value) : 0) < Logger.FATAL) {
			return;
		}
		this.writeMessage(this.fatalStreams, this.formatMessage(this.getPrefix(Logger.FATAL).plus0(this.formatContent(value().toString(), Logger.FATAL))), newLine);
	}

	async trace_async(value, newLine) {
		let flat_local_0 = flat_null;
		newLine = typeof newLine === 'undefined' ? true : newLine;
		if (((flat_local_0 = (this.accessor_loggingLevel())) !== flat_null ? (flat_local_0.value) : 0) < Logger.TRACE) {
			return;
		}
		this.writeMessage(this.traceStreams, this.formatMessage(this.getPrefix(Logger.TRACE).plus0(this.formatContent((await value()).toString(), Logger.TRACE))), newLine);
	}

	async debug_async(value, newLine) {
		let flat_local_0 = flat_null;
		newLine = typeof newLine === 'undefined' ? true : newLine;
		if (((flat_local_0 = (this.accessor_loggingLevel())) !== flat_null ? (flat_local_0.value) : 0) < Logger.DEBUG) {
			return;
		}
		this.writeMessage(this.debugStreams, this.formatMessage(this.getPrefix(Logger.DEBUG).plus0(this.formatContent((await value()).toString(), Logger.DEBUG))), newLine);
	}

	async info_async(value, newLine) {
		let flat_local_0 = flat_null;
		newLine = typeof newLine === 'undefined' ? true : newLine;
		if (((flat_local_0 = (this.accessor_loggingLevel())) !== flat_null ? (flat_local_0.value) : 0) < Logger.INFO) {
			return;
		}
		this.writeMessage(this.infoStreams, this.formatMessage(this.getPrefix(Logger.INFO).plus0(this.formatContent((await value()).toString(), Logger.INFO))), newLine);
	}

	async warn_async(value, newLine) {
		let flat_local_0 = flat_null;
		newLine = typeof newLine === 'undefined' ? true : newLine;
		if (((flat_local_0 = (this.accessor_loggingLevel())) !== flat_null ? (flat_local_0.value) : 0) < Logger.WARN) {
			return;
		}
		this.writeMessage(this.warnStreams, this.formatMessage(this.getPrefix(Logger.WARN).plus0(this.formatContent((await value()).toString(), Logger.WARN))), newLine);
	}

	async error_async(value, newLine) {
		let flat_local_0 = flat_null;
		newLine = typeof newLine === 'undefined' ? true : newLine;
		if (((flat_local_0 = (this.accessor_loggingLevel())) !== flat_null ? (flat_local_0.value) : 0) < Logger.ERROR) {
			return;
		}
		this.writeMessage(this.errorStreams, this.formatMessage(this.getPrefix(Logger.ERROR).plus0(this.formatContent((await value()).toString(), Logger.ERROR))), newLine);
	}

	async fatal_async(value, newLine) {
		let flat_local_0 = flat_null;
		newLine = typeof newLine === 'undefined' ? true : newLine;
		if (((flat_local_0 = (this.accessor_loggingLevel())) !== flat_null ? (flat_local_0.value) : 0) < Logger.FATAL) {
			return;
		}
		this.writeMessage(this.fatalStreams, this.formatMessage(this.getPrefix(Logger.FATAL).plus0(this.formatContent((await value()).toString(), Logger.FATAL))), newLine);
	}

	static generated134(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44, value45, value46, value47, value48, value49, value50) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		temp[41] = value41;
		temp[42] = value42;
		temp[43] = value43;
		temp[44] = value44;
		temp[45] = value45;
		temp[46] = value46;
		temp[47] = value47;
		temp[48] = value48;
		temp[49] = value49;
		temp[50] = value50;
		return FlatArray.construct1(temp, 51);
	}

	static generated208(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		return FlatArray.construct1(temp, 39);
	}

	static generated269(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated270(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated271(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated272(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated273(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated274(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated282(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated283(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated284(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated285(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated286(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated287(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	accessor_loggingLevel() {
		if (Logger.forceLoggingLevel !== flat_null) {
			return Logger.forceLoggingLevel;
		} else if (this._loggingLevel !== flat_null) {
			return this._loggingLevel;
		} else {
			return this.accessor_level();
		}
	}

	mutator_loggingLevel(value) {
		this._loggingLevel = value;
		return value;
	}

	accessor_showPrefix() {
		if (Logger.forceShowPrefix !== flat_null) {
			return Logger.forceShowPrefix;
		} else if (this._showPrefix !== flat_null) {
			return this._showPrefix;
		} else {
			return Logger.defaultShowPrefix;
		}
	}

	mutator_showPrefix(value) {
		this._showPrefix = value;
		return value;
	}

	accessor_showFormatting() {
		if (Logger.forceShowFormatting !== flat_null) {
			return Logger.forceShowFormatting;
		} else if (this._showFormatting !== flat_null) {
			return this._showFormatting;
		} else {
			return Logger.defaultShowFormatting;
		}
	}

	mutator_showFormatting(value) {
		this._showFormatting = value;
		return value;
	}

	accessor_lineLength() {
		if (Logger.forceLineLength !== flat_null) {
			return Logger.forceLineLength;
		} else if (this._lineLength !== flat_null) {
			return this._lineLength;
		} else {
			return Logger.defaultLineLength;
		}
	}

	mutator_lineLength(value) {
		this._lineLength = value;
		return value;
	}

	accessor_linePrefix() {
		if (Logger.forceLinePrefix !== flat_null) {
			return Logger.forceLinePrefix;
		} else if (this._linePrefix !== flat_null) {
			return this._linePrefix;
		} else {
			return Logger.defaultLinePrefix;
		}
	}

	mutator_linePrefix(value) {
		this._linePrefix = value;
		return value;
	}

	accessor_lineSuffix() {
		if (Logger.forceLineSuffix !== flat_null) {
			return Logger.forceLineSuffix;
		} else if (this._lineSuffix !== flat_null) {
			return this._lineSuffix;
		} else {
			return Logger.defaultLineSuffix;
		}
	}

	mutator_lineSuffix(value) {
		this._lineSuffix = value;
		return value;
	}

	accessor_prefix() {
		if (Logger.forcePrefix !== flat_null) {
			return Logger.forcePrefix;
		} else if (this._prefix !== flat_null) {
			return this._prefix;
		} else {
			return Logger.defaultPrefix;
		}
	}

	mutator_prefix(value) {
		this._prefix = value;
		return value;
	}

	accessor_suffix() {
		if (Logger.forceSuffix !== flat_null) {
			return Logger.forceSuffix;
		} else if (this._suffix !== flat_null) {
			return this._suffix;
		} else {
			return Logger.defaultSuffix;
		}
	}

	mutator_suffix(value) {
		this._suffix = value;
		return value;
	}

	accessor_prefixSeparator() {
		if (Logger.forcePrefixSeparator !== flat_null) {
			return Logger.forcePrefixSeparator;
		} else if (this._prefixSeparator !== flat_null) {
			return this._prefixSeparator;
		} else {
			return Logger.defaultPrefixSeparator;
		}
	}

	mutator_prefixSeparator(value) {
		this._prefixSeparator = value;
		return value;
	}

	accessor_splitOnWord() {
		if (Logger.forceSplitOnWord !== flat_null) {
			return Logger.forceSplitOnWord;
		} else if (this._splitOnWord !== flat_null) {
			return this._splitOnWord;
		} else {
			return Logger.defaultSplitOnWord;
		}
	}

	mutator_splitOnWord(value) {
		this._splitOnWord = value;
		return value;
	}

	accessor_level() {
		if (this.lastDisabledLoggersModificationId === Logger.currentDisabledLoggersModificationId) {
			return this._level;
		}
		if ((this.loggerClass) !== flat_null) {
			this._level = FlatInt.construct(Logger.getClassLoggingLevel(this.loggerClass));
		} else if ((this.label) !== flat_null) {
			this._level = FlatInt.construct(Logger.getLabelLoggingLevel(this.label));
		} else {
			this._level = FlatInt.construct(Logger.accessor_defaultLoggingLevel());
		}
		this.lastDisabledLoggersModificationId = Logger.currentDisabledLoggersModificationId;
		return this._level;
	}

	static accessor_defaultLoggingLevel() {
		return Logger._defaultLoggingLevel;
	}

	static mutator_defaultLoggingLevel(value) {
		Logger._defaultLoggingLevel = value;
		Logger.currentDisabledLoggersModificationId++;
		return value;
	}

	static accessor__js_class() {
		return typeof Logger.__lazy_accessor__js_class !== 'undefined' ? Logger.__lazy_accessor__js_class : (Logger.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/log/Logger"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Logger.generated134(Field.construct(FlatString.construct5("loggingLevel")), Field.construct(FlatString.construct5("showPrefix")), Field.construct(FlatString.construct5("showFormatting")), Field.construct(FlatString.construct5("lineLength")), Field.construct(FlatString.construct5("linePrefix")), Field.construct(FlatString.construct5("lineSuffix")), Field.construct(FlatString.construct5("prefix")), Field.construct(FlatString.construct5("suffix")), Field.construct(FlatString.construct5("prefixSeparator")), Field.construct(FlatString.construct5("splitOnWord")), Field.construct(FlatString.construct5("disabled")), Field.construct(FlatString.construct5("level")), Field.construct(FlatString.construct5("traceStreams")), Field.construct(FlatString.construct5("debugStreams")), Field.construct(FlatString.construct5("infoStreams")), Field.construct(FlatString.construct5("warnStreams")), Field.construct(FlatString.construct5("errorStreams")), Field.construct(FlatString.construct5("fatalStreams")), Field.construct(FlatString.construct5("TRACE")), Field.construct(FlatString.construct5("DEBUG")), Field.construct(FlatString.construct5("INFO")), Field.construct(FlatString.construct5("WARN")), Field.construct(FlatString.construct5("ERROR")), Field.construct(FlatString.construct5("FATAL")), Field.construct(FlatString.construct5("OFF")), Field.construct(FlatString.construct5("defaultLoggingLevel")), Field.construct(FlatString.construct5("defaultShowPrefix")), Field.construct(FlatString.construct5("defaultShowFormatting")), Field.construct(FlatString.construct5("defaultLineLength")), Field.construct(FlatString.construct5("defaultSplitOnWord")), Field.construct(FlatString.construct5("defaultLinePrefix")), Field.construct(FlatString.construct5("defaultLineSuffix")), Field.construct(FlatString.construct5("defaultPrefix")), Field.construct(FlatString.construct5("defaultSuffix")), Field.construct(FlatString.construct5("defaultPrefixSeparator")), Field.construct(FlatString.construct5("forceLoggingLevel")), Field.construct(FlatString.construct5("forceShowPrefix")), Field.construct(FlatString.construct5("forceShowFormatting")), Field.construct(FlatString.construct5("forceLineLength")), Field.construct(FlatString.construct5("forceSplitOnWord")), Field.construct(FlatString.construct5("forceLinePrefix")), Field.construct(FlatString.construct5("forceLineSuffix")), Field.construct(FlatString.construct5("forcePrefix")), Field.construct(FlatString.construct5("forceSuffix")), Field.construct(FlatString.construct5("forcePrefixSeparator")), Field.construct(FlatString.construct5("defaultTraceStreams")), Field.construct(FlatString.construct5("defaultDebugStreams")), Field.construct(FlatString.construct5("defaultInfoStreams")), Field.construct(FlatString.construct5("defaultWarnStreams")), Field.construct(FlatString.construct5("defaultErrorStreams")), Field.construct(FlatString.construct5("defaultFatalStreams"))), Logger.generated208(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("resetLoggingLevels")), Function.construct(FlatString.construct5("disableLogs")), Function.construct(FlatString.construct5("setLoggingLevel")), Function.construct(FlatString.construct5("enableLogs")), Function.construct(FlatString.construct5("disableLogs")), Function.construct(FlatString.construct5("setLoggingLevel")), Function.construct(FlatString.construct5("enableLogs")), Function.construct(FlatString.construct5("isClassDisabled")), Function.construct(FlatString.construct5("isLabelDisabled")), Function.construct(FlatString.construct5("getClassLoggingLevel")), Function.construct(FlatString.construct5("getLabelLoggingLevel")), Function.construct(FlatString.construct5("getLevelFromString")), Function.construct(FlatString.construct5("getPrefix")), Function.construct(FlatString.construct5("addFilter")), Function.construct(FlatString.construct5("formatContent")), Function.construct(FlatString.construct5("wrapLine")), Function.construct(FlatString.construct5("addLinePrefix")), Function.construct(FlatString.construct5("addLineSuffix")), Function.construct(FlatString.construct5("addPrefix")), Function.construct(FlatString.construct5("addSuffix")), Function.construct(FlatString.construct5("formatMessage")), Function.construct(FlatString.construct5("increaseTab")), Function.construct(FlatString.construct5("decreaseTab")), Function.construct(FlatString.construct5("indent")), Function.construct(FlatString.construct5("writeMessage")), Function.construct(FlatString.construct5("trace")), Function.construct(FlatString.construct5("debug")), Function.construct(FlatString.construct5("info")), Function.construct(FlatString.construct5("warn")), Function.construct(FlatString.construct5("error")), Function.construct(FlatString.construct5("fatal")), Function.construct(FlatString.construct5("trace_async")), Function.construct(FlatString.construct5("debug_async")), Function.construct(FlatString.construct5("info_async")), Function.construct(FlatString.construct5("warn_async")), Function.construct(FlatString.construct5("error_async")), Function.construct(FlatString.construct5("fatal_async"))), this);
			})());
	}

	accessor_disabled() {
		let flat_local_0 = flat_null;
		return ((flat_local_0 = (this.accessor_level())) !== flat_null ? (flat_local_0.value) : 0) === Logger.OFF;
	}

	static __assignments() {
		this._loggingLevel = flat_null;
		this._showPrefix = flat_null;
		this._showFormatting = flat_null;
		this._lineLength = flat_null;
		this._linePrefix = flat_null;
		this._lineSuffix = flat_null;
		this._prefix = flat_null;
		this._suffix = flat_null;
		this._prefixSeparator = flat_null;
		this._splitOnWord = flat_null;
		this.filters = FlatArray.construct();
		this.lastDisabledLoggersModificationId = 0;
		this._level = FlatInt.construct(Logger.accessor_defaultLoggingLevel());
	}
}

class FlatLong extends Number {
	value = 0;
	static FILE_SIZES = flat_null;
	static MAX_VALUE = 0;
	static MIN_VALUE = 0;

	static construct(value) {
		let __value = new FlatLong();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Primitive.__assignments.apply(__value, [].slice.call(arguments));
		Number.__assignments.apply(__value, [].slice.call(arguments));
		FlatLong.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatLong.__init.call(__value, value);

		return __value;
	}

	destroy() {
	}

	static __init(value) {
		let self = this;

		this.value = value;
		return self;
	}

	static numDigits(number) {
		let size = number < 0 ? 2 : 1;
		number /= 10;
		while (number !== 0) {
			number /= 10;
			size++;
		}
		return size;
	}

	static toJson(value, format, tab) {
		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		return FlatLong.toString(value);
	}

	static toString(value) {
		return FlatString.construct4(FlatLong.toCharArray(value));
	}

	compareTo0(other) {
		let flat_local_0 = flat_null;
		return (this.value - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	compareToReal(other) {
		return (this.value - other);
	}

	compareToInteger(other) {
		return (this.value - other);
	}

	static compareTo(value, other) {
		return (value - other);
	}

	static compareToReal(value, other) {
		return (value - other);
	}

	static compareToInteger(value, other) {
		return (value - other);
	}

	plus0(other) {
		let flat_local_0 = flat_null;
		return FlatLong.construct(this.value + ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	plusReal(other) {
		return this.value + other;
	}

	plusInteger(other) {
		return this.value + other;
	}

	plusEquals0(other) {
		let flat_local_0 = flat_null;
		return FlatLong.construct(this.value += ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	plusEqualsReal(other) {
		return this.value += other;
	}

	plusEqualsInteger(other) {
		return this.value += other;
	}

	minus(other) {
		let flat_local_0 = flat_null;
		return FlatLong.construct(this.value - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	minusReal(other) {
		return this.value - other;
	}

	minusInteger(other) {
		return this.value - other;
	}

	minusEquals(other) {
		let flat_local_0 = flat_null;
		return FlatLong.construct(this.value -= ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	minusEqualsReal(other) {
		return this.value -= other;
	}

	minusEqualsInteger(other) {
		return this.value -= other;
	}

	multiply0(other) {
		let flat_local_0 = flat_null;
		return FlatLong.construct(this.value * ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	multiplyReal(other) {
		return this.value * other;
	}

	multiplyInteger(other) {
		return this.value * other;
	}

	multiplyEquals(other) {
		let flat_local_0 = flat_null;
		return FlatLong.construct(this.value *= ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	multiplyEqualsReal(other) {
		return this.value *= other;
	}

	multiplyEqualsInteger(other) {
		return this.value *= other;
	}

	equals(other) {
		let flat_local_0 = flat_null;
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(Number.accessor__js_class()) && ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0) === this.value;
	}

	equals0(other) {
		return this.value === other;
	}

	equalsReal(other) {
		return this.value === other;
	}

	equalsInteger(other) {
		return this.value === other;
	}

	static equals(value, other) {
		return value === other;
	}

	static equalsReal(value, other) {
		return value === other;
	}

	static equalsInteger(value, other) {
		return value === other;
	}

	toString() {
		return FlatLong.toString(this.value);
	}

	static hashCodeLong(value) {
		return FlatMath.abs0(value);
	}

	static plus(a, b) {
		return a + b;
	}

	toFileSize() {
		return FlatLong.toFileSize(this.value);
	}

	static toFileSize(size) {
		let index = 0;
		let current = size;
		while (current >= 1024 && index < FlatLong.FILE_SIZES.accessor_count() - 1) {
			current /= 1024;
			index++;
		}
		return (FlatDouble.toFixed(current, 1)).plus0(FlatString.construct5("").plus0((FlatLong.FILE_SIZES.get(index)).toString().plus0(FlatString.construct5(""))));
	}

	toEnglish() {
		return FlatLong.toEnglish(this.value);
	}

	static toEnglish(size) {
		let negative = size < 0;
		let negSize = -size;
		let initial = negative ? FlatLong.toString(negSize) : FlatLong.toString(size);
		let str = initial;
		{
			let i = 3;
			for (; i < initial.count; i += 3) {
				str = str.substring(0, initial.count - i).plus0(FlatString.construct5(",").plus0(str.substring(initial.count - i, undefined)));
			}
		}
		return negative ? FlatString.construct5("-").plus0((str).plus0(FlatString.construct5(""))) : str;
	}

	millisToTime(abbreviate, millis, seconds, minutes, hours, delimiter) {
		abbreviate = typeof abbreviate === 'undefined' ? true : abbreviate;
		millis = typeof millis === 'undefined' ? true : millis;
		seconds = typeof seconds === 'undefined' ? true : seconds;
		minutes = typeof minutes === 'undefined' ? true : minutes;
		hours = typeof hours === 'undefined' ? true : hours;
		delimiter = typeof delimiter === 'undefined' ? abbreviate ? FlatString.construct5(" ") : FlatString.construct5(", ") : delimiter;
		return FlatLong.millisToTime(this.value, abbreviate, millis, seconds, minutes, hours, delimiter);
	}

	static millisToTime(time, abbreviate, millis, seconds, minutes, hours, delimiter) {
		abbreviate = typeof abbreviate === 'undefined' ? true : abbreviate;
		millis = typeof millis === 'undefined' ? true : millis;
		seconds = typeof seconds === 'undefined' ? true : seconds;
		minutes = typeof minutes === 'undefined' ? true : minutes;
		hours = typeof hours === 'undefined' ? true : hours;
		delimiter = typeof delimiter === 'undefined' ? abbreviate ? FlatString.construct5(" ") : FlatString.construct5(", ") : delimiter;
		let str = FlatString.construct5("");
		let value = ~~(time % 1000);
		if (millis) {
			if (abbreviate) {
				str = FlatString.construct5("ms");
			} else {
				str = FlatString.construct5(" millisecond").plus0((value === 1 ? FlatString.construct5("") : FlatString.construct5("s")).plus0(FlatString.construct5("")));
			}
			str = FlatLong.toString((value)).plus0(FlatString.construct5("").plus0((str).plus0(FlatString.construct5(""))));
			if (time < 1000) {
				return str;
			}
		}
		time /= 1000;
		value = ~~(time % 60);
		if (seconds) {
			if (abbreviate) {
				str = FlatString.construct5("s").plus0((str.accessor_isNotEmpty() ? delimiter.plus0(str) : FlatString.construct5("")).plus0(FlatString.construct5("")));
			} else {
				str = FlatString.construct5(" second").plus0((value === 1 ? FlatString.construct5("") : FlatString.construct5("s")).plus0(FlatString.construct5(""))).plus0((str.accessor_isNotEmpty() ? delimiter.plus0(str) : FlatString.construct5("")));
			}
			str = FlatLong.toString((value)).plus0(FlatString.construct5("").plus0((str).plus0(FlatString.construct5(""))));
			if (time < 60) {
				return str;
			}
		}
		time /= 60;
		value = ~~(time % 60);
		if (minutes) {
			if (abbreviate) {
				str = FlatString.construct5("m").plus0((str.accessor_isNotEmpty() ? delimiter.plus0(str) : FlatString.construct5("")).plus0(FlatString.construct5("")));
			} else {
				str = FlatString.construct5(" minute").plus0((value === 1 ? FlatString.construct5("") : FlatString.construct5("s")).plus0(FlatString.construct5(""))).plus0((str.accessor_isNotEmpty() ? delimiter.plus0(str) : FlatString.construct5("")));
			}
			str = FlatLong.toString((value)).plus0(FlatString.construct5("").plus0((str).plus0(FlatString.construct5(""))));
			if (time < 60) {
				return str;
			}
		}
		time /= 60;
		value = ~~(time % 24);
		if (hours) {
			if (abbreviate) {
				str = FlatString.construct5("h").plus0((str.accessor_isNotEmpty() ? delimiter.plus0(str) : FlatString.construct5("")).plus0(FlatString.construct5("")));
			} else {
				str = FlatString.construct5(" hour").plus0((value === 1 ? FlatString.construct5("") : FlatString.construct5("s")).plus0(FlatString.construct5(""))).plus0((str.accessor_isNotEmpty() ? delimiter.plus0(str) : FlatString.construct5("")));
			}
			str = FlatLong.toString((value)).plus0(FlatString.construct5("").plus0((str).plus0(FlatString.construct5(""))));
			if (time < 24) {
				return str;
			}
		}
		time /= 24;
		if (abbreviate) {
			str = FlatString.construct5("d").plus0((str.accessor_isNotEmpty() ? delimiter.plus0(str) : FlatString.construct5("")).plus0(FlatString.construct5("")));
		} else {
			str = FlatString.construct5(" days").plus0((str.accessor_isNotEmpty() ? delimiter.plus0(str) : FlatString.construct5("")).plus0(FlatString.construct5("")));
		}
		str = FlatLong.toString((time)).plus0(FlatString.construct5("").plus0((str).plus0(FlatString.construct5(""))));
		return str;
	}

	static parseLong(num) {
		return parseInt(num.chars.data);
	}

	static toCharArray(value) {
		return value.toString();
	}

	static generated120(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated133(value0, value1, value2, value3, value4) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		return FlatArray.construct1(temp, 5);
	}

	static generated166(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44, value45, value46) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		temp[40] = value40;
		temp[41] = value41;
		temp[42] = value42;
		temp[43] = value43;
		temp[44] = value44;
		temp[45] = value45;
		temp[46] = value46;
		return FlatArray.construct1(temp, 47);
	}

	static generated275() {
		let temp = flat_null;
		temp = [];
		temp[0] = FlatString.construct5("B");
		temp[1] = FlatString.construct5("KiB");
		temp[2] = FlatString.construct5("MiB");
		temp[3] = FlatString.construct5("GiB");
		return FlatArray.construct1(temp, 4);
	}

	static accessor__js_class() {
		return typeof FlatLong.__lazy_accessor__js_class !== 'undefined' ? FlatLong.__lazy_accessor__js_class : (FlatLong.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/primitive/number/Long"), false, Number.accessor__js_class(), FlatLong.generated120(Integer.accessor__js_class()), FlatLong.generated133(Field.construct(FlatString.construct5("realValue")), Field.construct(FlatString.construct5("integerValue")), Field.construct(FlatString.construct5("value")), Field.construct(FlatString.construct5("MAX_VALUE")), Field.construct(FlatString.construct5("MIN_VALUE"))), FlatLong.generated166(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("numDigits")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("plusReal")), Function.construct(FlatString.construct5("plusInteger")), Function.construct(FlatString.construct5("plusEquals")), Function.construct(FlatString.construct5("plusEqualsReal")), Function.construct(FlatString.construct5("plusEqualsInteger")), Function.construct(FlatString.construct5("minus")), Function.construct(FlatString.construct5("minusReal")), Function.construct(FlatString.construct5("minusInteger")), Function.construct(FlatString.construct5("minusEquals")), Function.construct(FlatString.construct5("minusEqualsReal")), Function.construct(FlatString.construct5("minusEqualsInteger")), Function.construct(FlatString.construct5("multiply")), Function.construct(FlatString.construct5("multiplyReal")), Function.construct(FlatString.construct5("multiplyInteger")), Function.construct(FlatString.construct5("multiplyEquals")), Function.construct(FlatString.construct5("multiplyEqualsReal")), Function.construct(FlatString.construct5("multiplyEqualsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("hashCodeLong")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("toFileSize")), Function.construct(FlatString.construct5("toFileSize")), Function.construct(FlatString.construct5("toEnglish")), Function.construct(FlatString.construct5("toEnglish")), Function.construct(FlatString.construct5("millisToTime")), Function.construct(FlatString.construct5("millisToTime")), Function.construct(FlatString.construct5("parseLong")), Function.construct(FlatString.construct5("toCharArray"))), this);
			})());
	}

	accessor_realValue() {
		return this.value;
	}

	accessor_integerValue() {
		return this.value;
	}

	static __assignments() {
	}
}

class MapIterator extends StreamIterator {
	funcs = flat_null;
	_next = flat_null;
	_current = flat_null;
	savedNext = false;

	static construct(iterator, func) {
		let __value = new MapIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StreamIterator.__assignments.apply(__value, [].slice.call(arguments));
		MapIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = MapIterator.__init.call(__value, iterator, func);

		return __value;
	}

	destroy() {
	}

	static __init(iterator, func) {
		let self = this;

		this.funcs.add0(func);
		self = StreamIterator.__init.call(this, iterator);
		return self;
	}

	static generated110(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated164(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	accessor_stepNext() {
		let value = this.accessor_next();
		this.iterator.accessor_stepNext();
		this.savedNext = false;
		return value;
	}

	accessor_next() {
		let self = this;

		if (this.savedNext) {
			return this._next;
		}
		this._current = this._next;
		this._next = this.funcs.map1((_x, _i, _array) => {
				return _x(self.iterator.accessor_next());
		}).accessor_first();
		this.savedNext = true;
		return this._next;
	}

	static accessor__js_class() {
		return typeof MapIterator.__lazy_accessor__js_class !== 'undefined' ? MapIterator.__lazy_accessor__js_class : (MapIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/stream/MapIterator"), false, StreamIterator.accessor__js_class(), FlatArray.construct(), MapIterator.generated110(Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("current"))), MapIterator.generated164(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor_hasNext() {
		return this.savedNext || this.iterator.accessor_hasNext();
	}

	accessor_current() {
		return this._current;
	}

	static __assignments() {
		this.funcs = FlatArray.construct();
		this._next = flat_null;
		this._current = flat_null;
		this.savedNext = false;
	}
}

class Match extends FlatObject {
	source = flat_null;
	start = 0;
	end = 0;

	static construct(source, start, end) {
		let __value = new Match();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Match.__assignments.apply(__value, [].slice.call(arguments));
		__value = Match.__init.call(__value, source, start, end);

		return __value;
	}

	destroy() {
	}

	static __init(source, start, end) {
		let self = this;

		this.source = source;
		this.start = start;
		this.end = end;
		return self;
	}

	toString() {
		return this.accessor_match();
	}

	static generated91(value0, value1, value2, value3, value4) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		return FlatArray.construct1(temp, 5);
	}

	static generated170(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static accessor__js_class() {
		return typeof Match.__lazy_accessor__js_class !== 'undefined' ? Match.__lazy_accessor__js_class : (Match.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/regex/Match"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Match.generated91(Field.construct(FlatString.construct5("count")), Field.construct(FlatString.construct5("match")), Field.construct(FlatString.construct5("source")), Field.construct(FlatString.construct5("start")), Field.construct(FlatString.construct5("end"))), Match.generated170(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("toString"))), this);
			})());
	}

	accessor_count() {
		return this.end - this.start;
	}

	accessor_match() {
		return this.source.substring(this.start, this.end);
	}

	static __assignments() {
	}
}

class MatchGroup extends FlatObject {
	value = flat_null;
	matchIndex = 0;
	groupIndex = 0;

	static construct(value, matchIndex, groupIndex) {
		let __value = new MatchGroup();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		MatchGroup.__assignments.apply(__value, [].slice.call(arguments));
		__value = MatchGroup.__init.call(__value, value, matchIndex, groupIndex);

		return __value;
	}

	static __init(value, matchIndex, groupIndex) {
		let self = this;

		value = typeof value === 'undefined' ? this.value : value;
		matchIndex = typeof matchIndex === 'undefined' ? this.matchIndex : matchIndex;
		groupIndex = typeof groupIndex === 'undefined' ? this.groupIndex : groupIndex;
		this.groupIndex = groupIndex;
		this.matchIndex = matchIndex;
		this.value = value;
		this.matchIndex = matchIndex;
		this.groupIndex = groupIndex;
		return self;
	}

	copy(value, matchIndex, groupIndex) {
		value = typeof value === 'undefined' ? this.value : value;
		matchIndex = typeof matchIndex === 'undefined' ? this.matchIndex : matchIndex;
		groupIndex = typeof groupIndex === 'undefined' ? this.groupIndex : groupIndex;
		return MatchGroup.construct(value, matchIndex, groupIndex);
	}

	equals(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(MatchGroup.accessor__js_class()) && this.equals0(other);
	}

	equals0(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && (this.value.equals(other.value)) && (this.matchIndex === other.matchIndex) && (this.groupIndex === other.groupIndex);
	}

	toString() {
		return FlatString.construct5("MatchGroup {\n  \"value\": ").plus0((this.value !== flat_null && this.value.accessor__js_class().isOfType(FlatString.accessor__js_class()) ? Char.toString('"').plus0(this.value.toString().plus0(Char.toString('"'))) : this.value.toString()).plus0(FlatString.construct5(",\n  \"matchIndex\": ").plus0((true ? Char.toString('"').plus0(FlatInt.toString(this.matchIndex).plus0(Char.toString('"'))) : FlatInt.toString(this.matchIndex)).plus0(FlatString.construct5(",\n  \"groupIndex\": ").plus0((true ? Char.toString('"').plus0(FlatInt.toString(this.groupIndex).plus0(Char.toString('"'))) : FlatInt.toString(this.groupIndex)).plus0(FlatString.construct5("\n}")))))));
	}

	toJson(format, tab) {
		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		return format ? FlatString.construct5("{\n").plus0((tab).plus0(FlatString.construct5("\"value\":").plus0((format ? FlatString.construct5(" ") : FlatString.construct5("")).plus0(FlatString.construct5("").plus0((this.value.toJson(format, tab)).plus0(FlatString.construct5(",").plus0((format ? FlatString.construct5("\n").plus0((tab).plus0(FlatString.construct5(""))) : FlatString.construct5("")).plus0(FlatString.construct5("\"matchIndex\":").plus0((format ? FlatString.construct5(" ") : FlatString.construct5("")).plus0(FlatString.construct5("").plus0((FlatInt.toJson(this.matchIndex, format, tab)).plus0(FlatString.construct5(",").plus0((format ? FlatString.construct5("\n").plus0((tab).plus0(FlatString.construct5(""))) : FlatString.construct5("")).plus0(FlatString.construct5("\"groupIndex\":").plus0((format ? FlatString.construct5(" ") : FlatString.construct5("")).plus0(FlatString.construct5("").plus0((FlatInt.toJson(this.groupIndex, format, tab)).plus0(FlatString.construct5("\n}"))))))))))))))))))) : FlatString.construct5("{\"value\":").plus0((format ? FlatString.construct5(" ") : FlatString.construct5("")).plus0(FlatString.construct5("").plus0((this.value.toJson(format, tab)).plus0(FlatString.construct5(",").plus0((format ? FlatString.construct5("\n").plus0((tab).plus0(FlatString.construct5(""))) : FlatString.construct5("")).plus0(FlatString.construct5("\"matchIndex\":").plus0((format ? FlatString.construct5(" ") : FlatString.construct5("")).plus0(FlatString.construct5("").plus0((FlatInt.toJson(this.matchIndex, format, tab)).plus0(FlatString.construct5(",").plus0((format ? FlatString.construct5("\n").plus0((tab).plus0(FlatString.construct5(""))) : FlatString.construct5("")).plus0(FlatString.construct5("\"groupIndex\":").plus0((format ? FlatString.construct5(" ") : FlatString.construct5("")).plus0(FlatString.construct5("").plus0((FlatInt.toJson(this.groupIndex, format, tab)).plus0(FlatString.construct5("}")))))))))))))))));
	}

	toBuilder() {
		return flat_regex_MatchGroup_Builder.construct(this.value, this.matchIndex, this.groupIndex);
	}

	copyTo(target, value, matchIndex, groupIndex) {
		value = typeof value === 'undefined' ? this.value : value;
		matchIndex = typeof matchIndex === 'undefined' ? this.matchIndex : matchIndex;
		groupIndex = typeof groupIndex === 'undefined' ? this.groupIndex : groupIndex;
		return target.toBuilder().value(value).matchIndex(matchIndex).groupIndex(groupIndex).build();
	}

	static generated144(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated155(value0, value1, value2, value3, value4, value5, value6, value7, value8) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		return FlatArray.construct1(temp, 9);
	}

	static accessor__js_class() {
		return typeof MatchGroup.__lazy_accessor__js_class !== 'undefined' ? MatchGroup.__lazy_accessor__js_class : (MatchGroup.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/regex/MatchGroup"), false, FlatObject.accessor__js_class(), FlatArray.construct(), MatchGroup.generated144(Field.construct(FlatString.construct5("value")), Field.construct(FlatString.construct5("matchIndex")), Field.construct(FlatString.construct5("groupIndex"))), MatchGroup.generated155(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("copy")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("toBuilder")), Function.construct(FlatString.construct5("copyTo"))), this);
			})());
	}

	static __assignments() {
	}
	static flat_regex_MatchGroup_Builder
}

class flat_regex_MatchGroup_Builder extends FlatObject {
	value_value = flat_null;
	matchIndex_value = 0;
	groupIndex_value = 0;

	static construct(value, matchIndex, groupIndex) {
		let __value = new flat_regex_MatchGroup_Builder();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		flat_regex_MatchGroup_Builder.__assignments.apply(__value, [].slice.call(arguments));
		__value = flat_regex_MatchGroup_Builder.__init.call(__value, value, matchIndex, groupIndex);

		return __value;
	}

	value(value) {
		this.value_value = value;
		return this;
	}

	matchIndex(value) {
		this.matchIndex_value = value;
		return this;
	}

	groupIndex(value) {
		this.groupIndex_value = value;
		return this;
	}

	build() {
		return MatchGroup.construct(this.value_value, this.matchIndex_value, this.groupIndex_value);
	}

	static __init(value, matchIndex, groupIndex) {
		let self = this;

		value = typeof value === 'undefined' ? this.value_value : value;
		matchIndex = typeof matchIndex === 'undefined' ? this.matchIndex_value : matchIndex;
		groupIndex = typeof groupIndex === 'undefined' ? this.groupIndex_value : groupIndex;
		this.value_value = value;
		this.matchIndex_value = matchIndex;
		this.groupIndex_value = groupIndex;
		return self;
	}

	static generated180(value0, value1, value2, value3, value4, value5) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		return FlatArray.construct1(temp, 6);
	}

	static accessor__js_class() {
		return typeof flat_regex_MatchGroup_Builder.__lazy_accessor__js_class !== 'undefined' ? flat_regex_MatchGroup_Builder.__lazy_accessor__js_class : (flat_regex_MatchGroup_Builder.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/regex/MatchGroup.Builder"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), flat_regex_MatchGroup_Builder.generated180(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("value")), Function.construct(FlatString.construct5("matchIndex")), Function.construct(FlatString.construct5("groupIndex")), Function.construct(FlatString.construct5("build")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class FlatMath extends FlatObject {
	static PI = 0;

	static construct() {
		let __value = new FlatMath();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatMath.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatMath.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static max0(a, b) {
		return a > b ? a : b;
	}

	static max1(a, b) {
		return a > b ? a : b;
	}

	static min0(a, b) {
		return a < b ? a : b;
	}

	static min1(a, b) {
		return a < b ? a : b;
	}

	static sign(num) {
		return num > 0 ? 1 : (num < 0 ? -1 : 0);
	}

	static abs0(number) {
		return number < 0 ? -number : number;
	}

	static abs1(number) {
		return number < 0 ? -number : number;
	}

	static abs2(number) {
		return number < 0 ? -number : number;
	}

	static gcd(a, b) {
		let r = ~~(a % b);
		if (r === 0) {
			return b;
		} else {
			return FlatMath.gcd(b, r);
		}
	}

	static __init() {
		let self = this;

		return self;
	}

	static sqrt(number) {
		return Math.sqrt(number);
	}

	static pow(base, power) {
		return Math.pow(base, power);
	}

	static sin(number) {
		return Math.sin(number);
	}

	static cos(number) {
		return Math.cos(number);
	}

	static tan(number) {
		return Math.tan(number);
	}

	static asin(number) {
		return Math.asin(number);
	}

	static acos(number) {
		return Math.acos(number);
	}

	static atan(number) {
		return Math.atan(number);
	}

	static round(number) {
		return FlatMath.floor(number + 0.5);
	}

	static random(range) {
		return ~~(Math.random() * range);
	}

	static floor(number) {
		return Math.floor(number);
	}

	static ceil(number) {
		return Math.ceil(number);
	}

	static generated102(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated131(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		return FlatArray.construct1(temp, 23);
	}

	static accessor__js_class() {
		return typeof FlatMath.__lazy_accessor__js_class !== 'undefined' ? FlatMath.__lazy_accessor__js_class : (FlatMath.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/Math"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatMath.generated102(Field.construct(FlatString.construct5("PI"))), FlatMath.generated131(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("max")), Function.construct(FlatString.construct5("max")), Function.construct(FlatString.construct5("min")), Function.construct(FlatString.construct5("min")), Function.construct(FlatString.construct5("sign")), Function.construct(FlatString.construct5("abs")), Function.construct(FlatString.construct5("abs")), Function.construct(FlatString.construct5("abs")), Function.construct(FlatString.construct5("gcd")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("sqrt")), Function.construct(FlatString.construct5("pow")), Function.construct(FlatString.construct5("sin")), Function.construct(FlatString.construct5("cos")), Function.construct(FlatString.construct5("tan")), Function.construct(FlatString.construct5("asin")), Function.construct(FlatString.construct5("acos")), Function.construct(FlatString.construct5("atan")), Function.construct(FlatString.construct5("round")), Function.construct(FlatString.construct5("random")), Function.construct(FlatString.construct5("floor")), Function.construct(FlatString.construct5("ceil"))), this);
			})());
	}

	static __assignments() {
	}
}

class NotEqualToOperator {
	static generated90(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof NotEqualToOperator.__lazy_accessor__js_class !== 'undefined' ? NotEqualToOperator.__lazy_accessor__js_class : (NotEqualToOperator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/operators/NotEqualToOperator"), true, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), NotEqualToOperator.generated90(Function.construct(FlatString.construct5("notEqualTo"))), this);
			})());
	}
}

class FlatString extends FlatObject {
	count = 0;
	chars = flat_null;
	static WHITESPACE = flat_null;

	static construct0(chars) {
		let __value = new FlatString();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatString.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatString.__init0.call(__value, chars);

		return __value;
	}

	static construct1(chars) {
		let __value = new FlatString();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatString.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatString.__init1.call(__value, chars);

		return __value;
	}

	static construct2(c) {
		let __value = new FlatString();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatString.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatString.__init2.call(__value, c);

		return __value;
	}

	static construct3(chars, count) {
		let __value = new FlatString();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatString.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatString.__init3.call(__value, chars, count);

		return __value;
	}

	static construct4(chars) {
		let __value = new FlatString();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatString.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatString.__init4.call(__value, chars);

		return __value;
	}

	static construct5(str) {
		let __value = new FlatString();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatString.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatString.__init5.call(__value, str);

		return __value;
	}

	destroy() {
	}

	static __init0(chars) {
		let self = this;

		this.count = chars.accessor_count();
		this.chars = StringCharArray.construct1(chars);
		return self;
	}

	static __init1(chars) {
		let self = this;

		this.count = chars.count;
		this.chars = chars;
		return self;
	}

	plus0(another) {
		return this.concat(another);
	}

	multiply0(times) {
		let flat_local_0 = flat_null;
		return this.repeat(((flat_local_0 = (times)) !== flat_null ? (flat_local_0.value) : 0));
	}

	repeat(times) {
		let self = this;

		return FlatArray.construct0(times, undefined).map1((_x, _i, _array) => {
				return this;
		}).join(undefined, undefined, undefined, undefined);
	}

	equals(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(FlatString.accessor__js_class()) && this.equals0(other);
	}

	equals0(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && this.count === other.count && this.compareTo0(other) === 0;
	}

	equalsIgnoreCase(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && this.count === other.count && this.toLowerCase().compareTo0(other.toLowerCase()) === 0;
	}

	replace(search, replacement, start) {
		start = typeof start === 'undefined' ? 0 : start;
		let output = this;
		let index = start;
		let offset = 0;
		while ((index = output.indexOf2(search, index + offset, undefined, undefined)) >= 0) {
			output = output.substring(0, index).plus0(replacement.plus0(output.substring(index + search.count, undefined)));
			offset = replacement.count;
		}
		return output;
	}

	indent(tabCount, tab, firstLine) {
		tabCount = typeof tabCount === 'undefined' ? 1 : tabCount;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		firstLine = typeof firstLine === 'undefined' ? true : firstLine;
		let value = this;
		value = value.replace(FlatString.construct5("\n"), FlatString.construct5("\n").plus0(tab.repeat(tabCount)), undefined);
		if (firstLine) {
			value = tab.repeat(tabCount).plus0(value);
		}
		return value;
	}

	isWhitespace() {
		{
			let i = 0;
			for (; i < this.count; i++) {
				let c = this.chars.get(i);
				switch (c) {
					case ' ':
					case '\n':
					case '\t':
					case '\r':
					continue;
					default:
					return false;

				}
			}
		}
		return true;
	}

	startsWith0(search) {
		return this.indexOf0(search, undefined, undefined, undefined) === 0;
	}

	startsWith1(search) {
		return this.indexOf2(search, undefined, undefined, undefined) === 0;
	}

	contains0(search) {
		return this.indexOf0(search, undefined, undefined, undefined) >= 0;
	}

	contains1(search) {
		return this.indexOf2(search, undefined, undefined, undefined) >= 0;
	}

	endsWith0(search) {
		return this.count > 0 && this.lastIndexOf0(search, undefined, undefined) === this.count - 1;
	}

	endsWith1(search) {
		return this.count >= search.count && this.lastIndexOf2(search, undefined, undefined) === this.count - search.count;
	}

	indexOf0(search, start, direction, defaultReturnValue) {
		start = typeof start === 'undefined' ? 0 : start;
		direction = typeof direction === 'undefined' ? 1 : direction;
		defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
		return this.indexOf2(Char.toString((search)).plus0(FlatString.construct5("")), start, direction, defaultReturnValue);
	}

	indexOf1(searches, start, direction, defaultReturnValue) {
		start = typeof start === 'undefined' ? 0 : start;
		direction = typeof direction === 'undefined' ? 1 : direction;
		defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
		let i = start;
		while (i < this.count && i >= 0) {
			let flat_local_0 = flat_null;
			let j = 0;
			while (j < searches.accessor_count()) {
				if (((flat_local_0 = (searches.get(j))) !== flat_null ? (flat_local_0.value) : 0) === this.chars.get(i)) {
					return i;
				}
				j++;
			}
			i += direction;
		}
		return defaultReturnValue;
	}

	indexOf2(search, start, direction, defaultReturnValue) {
		start = typeof start === 'undefined' ? 0 : start;
		direction = typeof direction === 'undefined' ? 1 : direction;
		defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
		let i = start;
		while (i < this.count && i + search.count <= this.count && i >= 0) {
			let found = true;
			let j = 0;
			while (j < search.count && i + j < this.count && found) {
				if (search.chars.get(j) !== this.chars.get(i + j)) {
					found = false;
				}
				j++;
			}
			if (found) {
				return i;
			}
			i += direction;
		}
		return defaultReturnValue;
	}

	lastIndexOf0(search, start, defaultReturnValue) {
		start = typeof start === 'undefined' ? this.count - 1 : start;
		defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
		return this.lastIndexOf1(FlatArray.construct().__chain('add0', [Char.construct(search)]), start, defaultReturnValue);
	}

	lastIndexOf1(searches, start, defaultReturnValue) {
		start = typeof start === 'undefined' ? this.count - 1 : start;
		defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
		return this.indexOf1(searches, start, -1, defaultReturnValue);
	}

	lastIndexOf2(search, start, defaultReturnValue) {
		start = typeof start === 'undefined' ? this.count - search.count : start;
		defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
		return this.indexOf2(search, start, -1, defaultReturnValue);
	}

	validateSubstringBounds(start, end, throwException) {
		throwException = typeof throwException === 'undefined' ? false : throwException;
		if (end - start <= 0) {
			if (end - start < 0) {
				if (throwException) {
					throw Exception.construct(FlatString.construct5("Substring bounds of [").plus0(FlatInt.toString((start)).plus0(FlatString.construct5(", ").plus0(FlatInt.toString((end)).plus0(FlatString.construct5("] are invalid"))))));
				}
				return false;
			}
		}
		return true;
	}

	trimStart0(count) {
		return this.substring(count, undefined);
	}

	trimEnd0(count) {
		return this.substring(undefined, this.count - count);
	}

	trimStart1(start, charsToTrim) {
		start = typeof start === 'undefined' ? 0 : start;
		charsToTrim = typeof charsToTrim === 'undefined' ? FlatString.WHITESPACE : charsToTrim;
		while (start <= this.count - 1 && charsToTrim.contains0(Char.construct(this.chars.get(start)))) {
			start++;
		}
		return start > this.count - 1 ? FlatString.construct5("") : this.substring(start, this.count);
	}

	trimEnd1(end, charsToTrim) {
		end = typeof end === 'undefined' ? this.count - 1 : end;
		charsToTrim = typeof charsToTrim === 'undefined' ? FlatString.WHITESPACE : charsToTrim;
		while (end >= 0 && charsToTrim.contains0(Char.construct(this.chars.get(end)))) {
			end--;
		}
		return end < 0 ? FlatString.construct5("") : this.substring(0, end + 1);
	}

	trimStart2(charToTrim, start) {
		start = typeof start === 'undefined' ? 0 : start;
		return this.trimStart1(start, this.generated261(charToTrim));
	}

	trimEnd2(charToTrim, end) {
		end = typeof end === 'undefined' ? this.count - 1 : end;
		return this.trimEnd1(end, this.generated262(charToTrim));
	}

	trimSurrounding(charToTrim, count) {
		count = typeof count === 'undefined' ? -1 : count;
		if ((count === -1 || count > 0) && this.accessor_first() === charToTrim && this.accessor_last() === charToTrim) {
			return this.trimEnds(1, undefined).trimSurrounding(charToTrim, count - 1);
		} else {
			return this;
		}
	}

	trimEnds(count, trimWhitespace) {
		trimWhitespace = typeof trimWhitespace === 'undefined' ? false : trimWhitespace;
		return trimWhitespace ? this.substring(count, this.count - count).trim(undefined, undefined, undefined) : this.substring(count, this.count - count);
	}

	trim(start, end, charsToTrim) {
		start = typeof start === 'undefined' ? 0 : start;
		end = typeof end === 'undefined' ? this.count - 1 : end;
		charsToTrim = typeof charsToTrim === 'undefined' ? FlatString.WHITESPACE : charsToTrim;
		while (start <= end && charsToTrim.contains0(Char.construct(this.chars.get(start)))) {
			start++;
		}
		while (end >= start && charsToTrim.contains0(Char.construct(this.chars.get(end)))) {
			end--;
		}
		return start > end ? FlatString.construct5("") : this.substring(start, end + 1);
	}

	toLowerCase() {
		return this.transform(Char.toLowerCase);
	}

	toUpperCase() {
		return this.transform(Char.toUpperCase);
	}

	capitalize() {
		return this.count > 0 ? Char.toString(Char.toUpperCase(this.chars.get(0))).plus0(this.substring(1, undefined)) : this;
	}

	transform(transform) {
		let newData = flat_null;
		newData = "";
		{
			let i = 0;
			for (; i < this.count; i++) {
				newData += transform(this.chars.get(i), i);
			}
		}
		return FlatString.construct4(newData);
	}

	getStringBetween(before, after, start) {
		let s = 0;
		let e = 0;
		start = typeof start === 'undefined' ? 0 : start;
		return (s = this.indexOf2(before, start, undefined, undefined)) >= 0 && (e = this.indexOf2(after, s + 1, undefined, undefined)) > 0 ? this.substring(s + before.count, e) : flat_null;
	}

	reverse(symmetrical) {
		symmetrical = typeof symmetrical === 'undefined' ? false : symmetrical;
		let reversed = this.chars.reverse();
		if (symmetrical) {
			{
				let i = 0;
				for (; i < reversed.count; i++) {
					let c = 0;
					switch ((c = reversed.get(i))) {
						case '(':
						reversed.set(i, ')');
					break;
				case ')':
				reversed.set(i, '(');
				break;
				case '<':
				reversed.set(i, '>');
				break;
				case '>':
				reversed.set(i, '<');
				break;
				case '{':
				reversed.set(i, '}');
			break;
		case '}':
		reversed.set(i, '{');
		break;
		case '[':
		reversed.set(i, ']');
		break;
		case ']':
		reversed.set(i, '[');
		break;
		case '/':
		reversed.set(i, '\\');
		break;
		case '\\':
		reversed.set(i, '/');
		break;
	}
}}}
return FlatString.construct1(reversed);}

surroundWith(str, symmetrical) {
	symmetrical = typeof symmetrical === 'undefined' ? false : symmetrical;
	return (str).plus0(FlatString.construct5("").plus0((this).plus0(FlatString.construct5("").plus0((symmetrical ? str.reverse(undefined) : str).plus0(FlatString.construct5(""))))));
}

compareTo0(other) {
	return this.chars.data.localeCompare(other.chars.data);
}

getGroupedChars(chars, start, end, opposite) {
	start = typeof start === 'undefined' ? 0 : start;
	end = typeof end === 'undefined' ? this.count : end;
	opposite = typeof opposite === 'undefined' ? false : opposite;
	let i = start;
	while (start < end && chars.contains0(Char.construct(this.chars.get(i))) !== opposite) {
		i++;
	}
	return this.substring(start, i);
}

orElse(value) {
	return this.count === 0 ? value : this;
}

howMany(c) {
	let flat_local_0 = flat_null;
	let sum = 0;
	flat_local_0 = (this.chars.toCharArray()).accessor_iterator();
	while (flat_local_0.accessor_hasNext()) {
		let char = 0;
		let flat_local_1 = flat_null;
		char = ((flat_local_1 = (flat_local_0.accessor_stepNext())) !== flat_null ? (flat_local_1.value) : 0);
		if (c === char) {
			sum++;
		}
	}
	return sum;
}

split0(delimiter) {
	let strs = FlatArray.construct();
	let lastI = 0;
	{
		let i = this.indexOf2(delimiter, undefined, undefined, undefined);
		for (; i !== -1; i = this.indexOf2(delimiter, i + delimiter.count, undefined, undefined)) {
			strs.add0(this.substring(lastI, i));
			lastI = i + delimiter.count;
		}
	}
	strs.add0(this.substring(lastI, undefined));
	return strs;
}

split1(delimiter) {
	let strs = FlatArray.construct();
	let lastI = 0;
	{
		let i = this.indexOf0(delimiter, undefined, undefined, undefined);
		for (; i !== -1; i = this.indexOf0(delimiter, i + 1, undefined, undefined)) {
			strs.add0(this.substring(lastI, i));
			lastI = i + 1;
		}
	}
	strs.add0(this.substring(lastI, undefined));
	return strs;
}

toString() {
	return this;
}

toJson(format, tab) {
	format = typeof format === 'undefined' ? false : format;
	tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
	let sanitized = this.replace(FlatString.construct5("\\"), FlatString.construct5("\\\\"), undefined).replace(FlatString.construct5("\""), FlatString.construct5("\\\""), undefined);
	return FlatString.construct5("\"").plus0((sanitized).plus0(FlatString.construct5("\"")));
}

static __init2(c) {
	let self = this;

	let chars = flat_null;
	chars = [c];
	self = FlatString.__init4.call(this, chars);
	return self;
}

static __init3(chars, count) {
	let self = this;

	this.count = count;
	this.chars = StringCharArray.construct2(chars, count);
	return self;
}

static __init4(chars) {
	let self = this;

	this.count = this.calculateSize(chars);
	this.chars = StringCharArray.construct2(chars, this.count);
	return self;
}

static __init5(str) {
	let self = this;

	let chars = flat_null;
	chars = str;
	this.count = this.calculateSize(chars);
	this.chars = StringCharArray.construct2(chars, this.count);
	return self;
}

concat(str) {
	let buf = flat_null;
	buf = this.chars.data + str.chars.data;
	return FlatString.construct4(buf);
}

calculateSize(chars) {
	return chars.length;
}

substring(start, end) {
	let data = flat_null;
	start = typeof start === 'undefined' ? 0 : start;
	end = typeof end === 'undefined' ? this.count : end;
	if (!this.validateSubstringBounds(start, end, true)) {
		return flat_null;
	} else if (start === end) {
		return FlatString.construct5("");
	} else if (start === 0 && end === this.count) {
		return this;
	}
	data = this.chars.data.substring(start, end);
	return FlatString.construct4(data);
}

static generated121(value0, value1, value2) {
	let temp = flat_null;
	temp = [];
	temp[0] = value0;
	temp[1] = value1;
	temp[2] = value2;
	return FlatArray.construct1(temp, 3);
}

static generated175(value0, value1, value2, value3, value4, value5, value6, value7) {
	let temp = flat_null;
	temp = [];
	temp[0] = value0;
	temp[1] = value1;
	temp[2] = value2;
	temp[3] = value3;
	temp[4] = value4;
	temp[5] = value5;
	temp[6] = value6;
	temp[7] = value7;
	return FlatArray.construct1(temp, 8);
}

static generated205(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44, value45, value46, value47, value48, value49, value50, value51, value52, value53, value54, value55, value56, value57, value58, value59, value60) {
	let temp = flat_null;
	temp = [];
	temp[0] = value0;
	temp[1] = value1;
	temp[2] = value2;
	temp[3] = value3;
	temp[4] = value4;
	temp[5] = value5;
	temp[6] = value6;
	temp[7] = value7;
	temp[8] = value8;
	temp[9] = value9;
	temp[10] = value10;
	temp[11] = value11;
	temp[12] = value12;
	temp[13] = value13;
	temp[14] = value14;
	temp[15] = value15;
	temp[16] = value16;
	temp[17] = value17;
	temp[18] = value18;
	temp[19] = value19;
	temp[20] = value20;
	temp[21] = value21;
	temp[22] = value22;
	temp[23] = value23;
	temp[24] = value24;
	temp[25] = value25;
	temp[26] = value26;
	temp[27] = value27;
	temp[28] = value28;
	temp[29] = value29;
	temp[30] = value30;
	temp[31] = value31;
	temp[32] = value32;
	temp[33] = value33;
	temp[34] = value34;
	temp[35] = value35;
	temp[36] = value36;
	temp[37] = value37;
	temp[38] = value38;
	temp[39] = value39;
	temp[40] = value40;
	temp[41] = value41;
	temp[42] = value42;
	temp[43] = value43;
	temp[44] = value44;
	temp[45] = value45;
	temp[46] = value46;
	temp[47] = value47;
	temp[48] = value48;
	temp[49] = value49;
	temp[50] = value50;
	temp[51] = value51;
	temp[52] = value52;
	temp[53] = value53;
	temp[54] = value54;
	temp[55] = value55;
	temp[56] = value56;
	temp[57] = value57;
	temp[58] = value58;
	temp[59] = value59;
	temp[60] = value60;
	return FlatArray.construct1(temp, 61);
}

static generated261(value0) {
	let temp = flat_null;
	temp = [];
	temp[0] = Char.construct(value0);
	return FlatArray.construct1(temp, 1);
}

static generated262(value0) {
	let temp = flat_null;
	temp = [];
	temp[0] = Char.construct(value0);
	return FlatArray.construct1(temp, 1);
}

get(index) {
	return this.chars.get(index);
}

set(index, value) {
}

static generated276() {
	let temp = flat_null;
	temp = [];
	temp[0] = Char.construct(' ');
	temp[1] = Char.construct('\t');
	temp[2] = Char.construct('\n');
	temp[3] = Char.construct('\r');
	return FlatArray.construct1(temp, 4);
}

static accessor__js_class() {
	return typeof FlatString.__lazy_accessor__js_class !== 'undefined' ? FlatString.__lazy_accessor__js_class : (FlatString.__lazy_accessor__js_class = (() => {
				return Class.construct1(FlatString.construct5("flat/String"), false, FlatObject.accessor__js_class(), FlatString.generated121(Comparable.accessor__js_class(), PlusOperator.accessor__js_class(), MultiplyOperator.accessor__js_class()), FlatString.generated175(Field.construct(FlatString.construct5("count")), Field.construct(FlatString.construct5("chars")), Field.construct(FlatString.construct5("first")), Field.construct(FlatString.construct5("last")), Field.construct(FlatString.construct5("title")), Field.construct(FlatString.construct5("hashCodeLong")), Field.construct(FlatString.construct5("isEmpty")), Field.construct(FlatString.construct5("isNotEmpty"))), FlatString.generated205(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("multiply")), Function.construct(FlatString.construct5("repeat")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsIgnoreCase")), Function.construct(FlatString.construct5("replace")), Function.construct(FlatString.construct5("indent")), Function.construct(FlatString.construct5("isWhitespace")), Function.construct(FlatString.construct5("startsWith")), Function.construct(FlatString.construct5("startsWith")), Function.construct(FlatString.construct5("contains")), Function.construct(FlatString.construct5("contains")), Function.construct(FlatString.construct5("endsWith")), Function.construct(FlatString.construct5("endsWith")), Function.construct(FlatString.construct5("indexOf")), Function.construct(FlatString.construct5("indexOf")), Function.construct(FlatString.construct5("indexOf")), Function.construct(FlatString.construct5("lastIndexOf")), Function.construct(FlatString.construct5("lastIndexOf")), Function.construct(FlatString.construct5("lastIndexOf")), Function.construct(FlatString.construct5("validateSubstringBounds")), Function.construct(FlatString.construct5("trimStart")), Function.construct(FlatString.construct5("trimEnd")), Function.construct(FlatString.construct5("trimStart")), Function.construct(FlatString.construct5("trimEnd")), Function.construct(FlatString.construct5("trimStart")), Function.construct(FlatString.construct5("trimEnd")), Function.construct(FlatString.construct5("trimSurrounding")), Function.construct(FlatString.construct5("trimEnds")), Function.construct(FlatString.construct5("trim")), Function.construct(FlatString.construct5("toLowerCase")), Function.construct(FlatString.construct5("toUpperCase")), Function.construct(FlatString.construct5("capitalize")), Function.construct(FlatString.construct5("transform")), Function.construct(FlatString.construct5("getStringBetween")), Function.construct(FlatString.construct5("reverse")), Function.construct(FlatString.construct5("surroundWith")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("getGroupedChars")), Function.construct(FlatString.construct5("orElse")), Function.construct(FlatString.construct5("howMany")), Function.construct(FlatString.construct5("split")), Function.construct(FlatString.construct5("split")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("concat")), Function.construct(FlatString.construct5("calculateSize")), Function.construct(FlatString.construct5("substring"))), this);
		})());
}

accessor_first() {
	return this.chars.accessor_first();
}

accessor_last() {
	return this.chars.accessor_last();
}

accessor_title() {
	return this.capitalize();
}

accessor_hashCodeLong() {
	return this.chars.accessor_hashCodeLong();
}

accessor_isEmpty() {
	return this.count === 0;
}

accessor_isNotEmpty() {
	return this.count > 0;
}

static __assignments() {
}}

class Null extends FlatString {
	static construct() {
		let __value = new Null();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		FlatString.__assignments.apply(__value, [].slice.call(arguments));
		Null.__assignments.apply(__value, [].slice.call(arguments));
		__value = Null.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		self = FlatString.__init1.call(this, FlatString.construct5("null").chars);
		return self;
	}

	concat(other) {
		return FlatString.construct5("null").plus0((other).plus0(FlatString.construct5("")));
	}

	toJson(format, tab) {
		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		return this.toString();
	}

	equals(other) {
		return !((other) !== flat_null);
	}

	equals0(other) {
		return !((other) !== flat_null);
	}

	static generated89(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated187(value0, value1, value2, value3, value4, value5) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		return FlatArray.construct1(temp, 6);
	}

	static accessor__js_class() {
		return typeof Null.__lazy_accessor__js_class !== 'undefined' ? Null.__lazy_accessor__js_class : (Null.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/primitive/Null"), false, FlatString.accessor__js_class(), FlatArray.construct(), Null.generated89(Field.construct(FlatString.construct5("hashCodeLong"))), Null.generated187(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("concat")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals"))), this);
			})());
	}

	accessor_hashCodeLong() {
		return 0;
	}

	static __assignments() {
	}
}

class NullAccessException extends Exception {
	static construct(message) {
		let __value = new NullAccessException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		NullAccessException.__assignments.apply(__value, [].slice.call(arguments));
		__value = NullAccessException.__init.call(__value, message);

		return __value;
	}

	destroy() {
	}

	static __init(message) {
		let self = this;

		message = typeof message === 'undefined' ? FlatString.construct5("Cannot access property from a null Object") : message;
		self = Exception.__init.call(this, message);
		return self;
	}

	static generated113(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof NullAccessException.__lazy_accessor__js_class !== 'undefined' ? NullAccessException.__lazy_accessor__js_class : (NullAccessException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/exception/NullAccessException"), false, Exception.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), NullAccessException.generated113(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class Pair extends FlatObject {
	key = flat_null;
	value = flat_null;

	static construct(key, value) {
		let __value = new Pair();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Pair.__assignments.apply(__value, [].slice.call(arguments));
		__value = Pair.__init.call(__value, key, value);

		return __value;
	}

	destroy() {
	}

	static __init(key, value) {
		let self = this;

		this.key = key;
		this.value = value;
		return self;
	}

	equals(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(Pair.accessor__js_class()) && this.equals0(other);
	}

	equals0(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && this.key.equals(other.key) && this.value.equals(other.value);
	}

	toString() {
		return FlatString.construct5("Pair (").plus0((this.key).toString().plus0(FlatString.construct5(", ").plus0((this.value).toString().plus0(FlatString.construct5(")")))));
	}

	static generated96(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated109(value0, value1, value2, value3, value4) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		return FlatArray.construct1(temp, 5);
	}

	static accessor__js_class() {
		return typeof Pair.__lazy_accessor__js_class !== 'undefined' ? Pair.__lazy_accessor__js_class : (Pair.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/Pair"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Pair.generated96(Field.construct(FlatString.construct5("key")), Field.construct(FlatString.construct5("value"))), Pair.generated109(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("toString"))), this);
			})());
	}

	static __assignments() {
	}
}

class Pattern extends FlatObject {
	pattern = flat_null;
	regexObj = flat_null;

	static construct(pattern) {
		let __value = new Pattern();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Pattern.__assignments.apply(__value, [].slice.call(arguments));
		__value = Pattern.__init.call(__value, pattern);

		return __value;
	}

	destroy() {
	}

	toString() {
		return FlatString.construct5("/").plus0((this.pattern).plus0(FlatString.construct5("/g")));
	}

	static __init(pattern) {
		let self = this;

		this.pattern = pattern;
		this.regexObj = new RegExp(pattern.chars.data, "g");
		return self;
	}

	static generated183(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated191(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static accessor__js_class() {
		return typeof Pattern.__lazy_accessor__js_class !== 'undefined' ? Pattern.__lazy_accessor__js_class : (Pattern.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/regex/Pattern"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Pattern.generated183(Field.construct(FlatString.construct5("pattern")), Field.construct(FlatString.construct5("regexObj"))), Pattern.generated191(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class PeekIterator extends StreamIterator {
	funcs = flat_null;

	static construct(iterator, func) {
		let __value = new PeekIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StreamIterator.__assignments.apply(__value, [].slice.call(arguments));
		PeekIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = PeekIterator.__init.call(__value, iterator, func);

		return __value;
	}

	destroy() {
	}

	static __init(iterator, func) {
		let self = this;

		this.funcs.add0(func);
		self = StreamIterator.__init.call(this, iterator);
		return self;
	}

	static generated97(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated111(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	accessor_hasNext() {
		let self = this;

		if (this.iterator.accessor_hasNext()) {
			this.funcs.forEach1((_x, _i, _array) => {
					_x(self.iterator.accessor_next());
			});
			return true;
		}
		return false;
	}

	static accessor__js_class() {
		return typeof PeekIterator.__lazy_accessor__js_class !== 'undefined' ? PeekIterator.__lazy_accessor__js_class : (PeekIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/stream/PeekIterator"), false, StreamIterator.accessor__js_class(), FlatArray.construct(), PeekIterator.generated97(Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("current"))), PeekIterator.generated111(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor_stepNext() {
		return this.iterator.accessor_stepNext();
	}

	accessor_next() {
		return this.iterator.accessor_next();
	}

	accessor_current() {
		return this.iterator.accessor_current();
	}

	static __assignments() {
		this.funcs = FlatArray.construct();
	}
}

class Queue extends FlatObject {
	data = flat_null;

	static construct() {
		let __value = new Queue();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Queue.__assignments.apply(__value, [].slice.call(arguments));
		__value = Queue.__init.call(__value);

		return __value;
	}

	static construct0(data) {
		let __value = new Queue();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Queue.__assignments.apply(__value, [].slice.call(arguments));
		__value = Queue.__init0.call(__value, data);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		this.data = FlatArray.construct();
		return self;
	}

	static __init0(data) {
		let self = this;

		this.data = data.toArray();
		return self;
	}

	dequeue() {
		return this.data.remove0(0);
	}

	enqueue(element) {
		return this.data.add0(element);
	}

	reverse() {
		return Queue.construct0(this.data.reverse());
	}

	toString() {
		return FlatString.construct5("Queue [").plus0((this.join(FlatString.construct5(", "), undefined, undefined, undefined)).plus0(FlatString.construct5("]")));
	}

	static generated149(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated154(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated161(value0, value1, value2, value3, value4, value5, value6, value7) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		return FlatArray.construct1(temp, 8);
	}

	static accessor__js_class() {
		return typeof Queue.__lazy_accessor__js_class !== 'undefined' ? Queue.__lazy_accessor__js_class : (Queue.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/Queue"), false, FlatObject.accessor__js_class(), Queue.generated149(List.accessor__js_class()), Queue.generated154(Field.construct(FlatString.construct5("count")), Field.construct(FlatString.construct5("iterator")), Field.construct(FlatString.construct5("first")), Field.construct(FlatString.construct5("last"))), Queue.generated161(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("dequeue")), Function.construct(FlatString.construct5("enqueue")), Function.construct(FlatString.construct5("reverse")), Function.construct(FlatString.construct5("toString"))), this);
			})());
	}

	accessor_count() {
		return this.data.accessor_count();
	}

	accessor_iterator() {
		return ArrayIterator.construct(this.data);
	}

	accessor_first() {
		return this.data.accessor_first();
	}

	mutator_first(value) {
		this.data.mutator_first(value);
		return value;
	}

	accessor_last() {
		return this.data.accessor_last();
	}

	mutator_last(value) {
		this.data.mutator_last(value);
		return value;
	}

	static __assignments() {
	}
}

class Regex extends FlatObject {
	static construct() {
		let __value = new Regex();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Regex.__assignments.apply(__value, [].slice.call(arguments));
		__value = Regex.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static containsMatch0(pattern, search) {
		return Regex.containsMatch1(pattern.pattern, search);
	}

	static containsMatch1(regex, search) {
		return Regex.getMatches1(regex, search).accessor_count() > 0;
	}

	static getMatches0(pattern, search) {
		let count = 0;
		let start = 0;
		let str = flat_null;
		let matches = FlatArray.construct();
		var regexMatches = search.chars.data.matchAll(pattern.regexObj);
		for (var m of regexMatches) {
			start = m.index;
			count = m[0].length;
			matches.add0(Match.construct(search, start, start + count));
		}
		return matches;
	}

	static getMatches1(pattern, search) {
		return Regex.getMatches0(Pattern.construct(pattern), search);
	}

	static __init() {
		let self = this;

		return self;
	}

	static getMatchGroups(pattern, search) {
		let index = 0;
		let groupData = flat_null;
		let groupStr = flat_null;
		let matchIndex = 0;
		let matches = FlatArray.construct();
		var regexMatches = search.chars.data.matchAll(pattern.regexObj);
		for (var match of regexMatches) {
			match.forEach((group, i) => {
					groupData = group;
					groupStr = typeof group === 'undefined' ? flat_null : FlatString.construct4(groupData);
					index = i;
					matches.add0(MatchGroup.construct(groupStr, matchIndex, index));
					;
			});
			matchIndex++;
		}
		return matches;
	}

	static generated138(value0, value1, value2, value3, value4, value5, value6) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		return FlatArray.construct1(temp, 7);
	}

	static accessor__js_class() {
		return typeof Regex.__lazy_accessor__js_class !== 'undefined' ? Regex.__lazy_accessor__js_class : (Regex.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/regex/Regex"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), Regex.generated138(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("containsMatch")), Function.construct(FlatString.construct5("containsMatch")), Function.construct(FlatString.construct5("getMatches")), Function.construct(FlatString.construct5("getMatches")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("getMatchGroups"))), this);
			})());
	}

	static __assignments() {
	}
}

class RegexStringExtensions {
	static String_replace(pattern, replace) {
		let data = flat_null;
		data = this.chars.data.replace(pattern.regexObj, replace.chars.data);
		return FlatString.construct4(data);
	}

	static String_startsWith(search) {
		return this.__callExtension(RegexStringExtensions.String_indexOf, [search]) === 0;
	}

	static String_contains(search) {
		return this.__callExtension(RegexStringExtensions.String_indexOf, [search]) >= 0;
	}

	static String_endsWith(search) {
		return this.count > 0 && this.__callExtension(RegexStringExtensions.String_indexOf, [search]) === this.count - search.pattern.count;
	}

	static String_matches(pattern) {
		let matches = Regex.getMatches0(pattern, this);
		return matches.accessor_count() === 1 && matches.get(0).start === 0 && matches.get(0).end === this.count;
	}

	static String_indexOf(search, start, direction, defaultReturnValue) {
		start = typeof start === 'undefined' ? 0 : start;
		direction = typeof direction === 'undefined' ? 1 : direction;
		defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
		let first = Regex.getMatches0(search, this).accessor_first();
		if ((first) !== flat_null) {
			return first.start;
		}
		return defaultReturnValue;
	}

	static String_split(regex) {
		let self = this;

		let nativeStrs = flat_null;
		let count = 0;
		nativeStrs = this.chars.data.split(RegExp(regex.pattern.chars.data));
		count = nativeStrs.length;
		return FlatArray.construct0(count, undefined).map1((_x, _i, _array) => {
				let buf = flat_null;
				let length = 0;
				buf = nativeStrs[_i];
				length = buf.length;
				let array = StringCharArray.construct2(buf, length);
				return FlatString.construct1(array);
		});
	}

	static generated117(value0, value1, value2, value3, value4, value5, value6) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		return FlatArray.construct1(temp, 7);
	}

	static accessor__js_class() {
		return typeof RegexStringExtensions.__lazy_accessor__js_class !== 'undefined' ? RegexStringExtensions.__lazy_accessor__js_class : (RegexStringExtensions.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/regex/RegexStringExtensions"), false, flat_null, FlatArray.construct(), FlatArray.construct(), RegexStringExtensions.generated117(Function.construct(FlatString.construct5("replace")), Function.construct(FlatString.construct5("startsWith")), Function.construct(FlatString.construct5("contains")), Function.construct(FlatString.construct5("endsWith")), Function.construct(FlatString.construct5("matches")), Function.construct(FlatString.construct5("indexOf")), Function.construct(FlatString.construct5("split"))), this);
			})());
	}

	static __assignments() {
	}
}

class FlatShort extends Number {
	value = 0;

	static construct(value) {
		let __value = new FlatShort();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Primitive.__assignments.apply(__value, [].slice.call(arguments));
		Number.__assignments.apply(__value, [].slice.call(arguments));
		FlatShort.__assignments.apply(__value, [].slice.call(arguments));
		__value = FlatShort.__init.call(__value, value);

		return __value;
	}

	destroy() {
	}

	static __init(value) {
		let self = this;

		this.value = value;
		return self;
	}

	compareTo0(other) {
		let flat_local_0 = flat_null;
		return this.value - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0);
	}

	compareToReal(other) {
		return this.value - other;
	}

	compareToInteger(other) {
		return this.value - other;
	}

	static compareToReal(value, other) {
		return value - other;
	}

	static compareToInteger(value, other) {
		return value - other;
	}

	plus0(other) {
		let flat_local_0 = flat_null;
		return FlatShort.construct(this.value + ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	plusReal(other) {
		return this.value + other;
	}

	plusInteger(other) {
		return this.value + other;
	}

	plusEquals0(other) {
		let flat_local_0 = flat_null;
		return FlatShort.construct(this.value += ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	plusEqualsReal(other) {
		return this.value += other;
	}

	plusEqualsInteger(other) {
		return this.value += other;
	}

	minus(other) {
		let flat_local_0 = flat_null;
		return FlatShort.construct(this.value - ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	minusReal(other) {
		return this.value - other;
	}

	minusInteger(other) {
		return this.value - other;
	}

	minusEquals(other) {
		let flat_local_0 = flat_null;
		return FlatShort.construct(this.value -= ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	minusEqualsReal(other) {
		return this.value -= other;
	}

	minusEqualsInteger(other) {
		return this.value -= other;
	}

	multiply0(other) {
		let flat_local_0 = flat_null;
		return FlatShort.construct(this.value * ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	multiplyReal(other) {
		return this.value * other;
	}

	multiplyInteger(other) {
		return this.value * other;
	}

	multiplyEquals(other) {
		let flat_local_0 = flat_null;
		return FlatShort.construct(this.value *= ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0));
	}

	multiplyEqualsReal(other) {
		return this.value *= other;
	}

	multiplyEqualsInteger(other) {
		return this.value *= other;
	}

	equals(other) {
		let flat_local_0 = flat_null;
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(Number.accessor__js_class()) && ((flat_local_0 = (other)) !== flat_null ? (flat_local_0.value) : 0) === this.value;
	}

	equals0(other) {
		return this.value === other;
	}

	equalsReal(other) {
		return this.value === other;
	}

	equalsInteger(other) {
		return this.value === other;
	}

	static equals(value, other) {
		return value === other;
	}

	static equalsReal(value, other) {
		return value === other;
	}

	static equalsInteger(value, other) {
		return value === other;
	}

	toString() {
		return FlatShort.toString(this.value);
	}

	static numDigits(number) {
		return FlatLong.numDigits(number);
	}

	static toString(value) {
		return FlatLong.toString(value);
	}

	static toJson(value, format, tab) {
		format = typeof format === 'undefined' ? false : format;
		tab = typeof tab === 'undefined' ? FlatString.construct5("\t") : tab;
		return FlatShort.toString(value);
	}

	static hashCodeLong(value) {
		return FlatMath.abs0(value);
	}

	static plus(a, b) {
		return a + b;
	}

	toEnglish() {
		return FlatShort.toEnglish(this.value);
	}

	static toEnglish(value) {
		return FlatLong.toEnglish(value);
	}

	static generated107(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated160(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated184(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		temp[28] = value28;
		temp[29] = value29;
		temp[30] = value30;
		temp[31] = value31;
		temp[32] = value32;
		temp[33] = value33;
		temp[34] = value34;
		temp[35] = value35;
		temp[36] = value36;
		temp[37] = value37;
		temp[38] = value38;
		temp[39] = value39;
		return FlatArray.construct1(temp, 40);
	}

	static accessor__js_class() {
		return typeof FlatShort.__lazy_accessor__js_class !== 'undefined' ? FlatShort.__lazy_accessor__js_class : (FlatShort.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/primitive/number/Short"), false, Number.accessor__js_class(), FlatShort.generated107(Integer.accessor__js_class()), FlatShort.generated160(Field.construct(FlatString.construct5("realValue")), Field.construct(FlatString.construct5("integerValue")), Field.construct(FlatString.construct5("value"))), FlatShort.generated184(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("compareTo")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("compareToReal")), Function.construct(FlatString.construct5("compareToInteger")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("plusReal")), Function.construct(FlatString.construct5("plusInteger")), Function.construct(FlatString.construct5("plusEquals")), Function.construct(FlatString.construct5("plusEqualsReal")), Function.construct(FlatString.construct5("plusEqualsInteger")), Function.construct(FlatString.construct5("minus")), Function.construct(FlatString.construct5("minusReal")), Function.construct(FlatString.construct5("minusInteger")), Function.construct(FlatString.construct5("minusEquals")), Function.construct(FlatString.construct5("minusEqualsReal")), Function.construct(FlatString.construct5("minusEqualsInteger")), Function.construct(FlatString.construct5("multiply")), Function.construct(FlatString.construct5("multiplyReal")), Function.construct(FlatString.construct5("multiplyInteger")), Function.construct(FlatString.construct5("multiplyEquals")), Function.construct(FlatString.construct5("multiplyEqualsReal")), Function.construct(FlatString.construct5("multiplyEqualsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equalsReal")), Function.construct(FlatString.construct5("equalsInteger")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("numDigits")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("toJson")), Function.construct(FlatString.construct5("hashCodeLong")), Function.construct(FlatString.construct5("plus")), Function.construct(FlatString.construct5("toEnglish")), Function.construct(FlatString.construct5("toEnglish"))), this);
			})());
	}

	accessor_realValue() {
		return this.value;
	}

	accessor_integerValue() {
		return this.value;
	}

	static __assignments() {
	}
}

class SkipIterator extends StreamIterator {
	position = 0;
	count = 0;

	static construct(iterator, count) {
		let __value = new SkipIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StreamIterator.__assignments.apply(__value, [].slice.call(arguments));
		SkipIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = SkipIterator.__init.call(__value, iterator, count);

		return __value;
	}

	destroy() {
	}

	static __init(iterator, count) {
		let self = this;

		self = StreamIterator.__init.call(this, iterator);
		this.count = count;
		return self;
	}

	static generated95(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated147(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	accessor_hasNext() {
		while (this.position < this.count && this.iterator.accessor_hasNext()) {
			this.position++;
			this.iterator.accessor_stepNext();
		}
		return this.iterator.accessor_hasNext();
	}

	static accessor__js_class() {
		return typeof SkipIterator.__lazy_accessor__js_class !== 'undefined' ? SkipIterator.__lazy_accessor__js_class : (SkipIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/stream/SkipIterator"), false, StreamIterator.accessor__js_class(), FlatArray.construct(), SkipIterator.generated95(Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("current"))), SkipIterator.generated147(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor_stepNext() {
		return this.iterator.accessor_stepNext();
	}

	accessor_next() {
		return this.iterator.accessor_next();
	}

	accessor_current() {
		return this.iterator.accessor_current();
	}

	static __assignments() {
		this.position = 0;
	}
}

class Stack extends FlatObject {
	top = flat_null;
	count = 0;

	static construct() {
		let __value = new Stack();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Stack.__assignments.apply(__value, [].slice.call(arguments));
		__value = Stack.__init.call(__value);

		return __value;
	}

	static construct0(data) {
		let __value = new Stack();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Stack.__assignments.apply(__value, [].slice.call(arguments));
		__value = Stack.__init0.call(__value, data);

		return __value;
	}

	static construct1(top) {
		let __value = new Stack();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Stack.__assignments.apply(__value, [].slice.call(arguments));
		__value = Stack.__init1.call(__value, top);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		return self;
	}

	static __init0(data) {
		let self = this;

		let flat_local_0 = flat_null;
		flat_local_0 = (data).accessor_iterator();
		while (flat_local_0.accessor_hasNext()) {
			let e = flat_null;
			e = flat_local_0.accessor_stepNext();
			this.push(e);
		}
		return self;
	}

	static __init1(top) {
		let self = this;

		this.top = top;
		return self;
	}

	push(data) {
		let node = ListNode.construct(data, this.top);
		this.top = node;
		this.count++;
		return data;
	}

	pop() {
		if (this.accessor_isEmpty()) {
			throw EmptyStackException.construct(undefined);
		}
		let data = this.top.data;
		this.top = this.top.next;
		this.count--;
		return data;
	}

	clear() {
		let copy = Stack.construct1(this.top);
		this.top = flat_null;
		this.count = 0;
		return copy;
	}

	peek(offset) {
		let flat_local_0 = flat_null;
		offset = typeof offset === 'undefined' ? 0 : offset;
		if (!((this.top) !== flat_null)) {
			return flat_null;
		}
		let current = this.top;
		while ((current) !== flat_null && offset-- > 0) {
			current = current.next;
		}
		return ((flat_local_0 = current) !== flat_null ? (flat_local_0.data) : flat_null);
	}

	copy() {
		return Stack.construct0(this.toArray());
	}

	toArray() {
		let array = FlatArray.construct0(this.count, undefined);
		let current = this.top;
		while (current !== flat_null) {
			array.unshift(current.data);
			current = current.next;
		}
		return array;
	}

	toString() {
		return this.toArray().toString();
	}

	static generated108(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated142(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		return FlatArray.construct1(temp, 13);
	}

	static accessor__js_class() {
		return typeof Stack.__lazy_accessor__js_class !== 'undefined' ? Stack.__lazy_accessor__js_class : (Stack.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/Stack"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Stack.generated108(Field.construct(FlatString.construct5("count")), Field.construct(FlatString.construct5("isEmpty")), Field.construct(FlatString.construct5("isNotEmpty"))), Stack.generated142(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("push")), Function.construct(FlatString.construct5("pop")), Function.construct(FlatString.construct5("clear")), Function.construct(FlatString.construct5("peek")), Function.construct(FlatString.construct5("copy")), Function.construct(FlatString.construct5("toArray")), Function.construct(FlatString.construct5("toString"))), this);
			})());
	}

	accessor_isEmpty() {
		return this.count === 0;
	}

	accessor_isNotEmpty() {
		return this.count > 0;
	}

	static __assignments() {
	}
}

class StackTrace extends FlatObject {
	static construct() {
		let __value = new StackTrace();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StackTrace.__assignments.apply(__value, [].slice.call(arguments));
		__value = StackTrace.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated156(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof StackTrace.__lazy_accessor__js_class !== 'undefined' ? StackTrace.__lazy_accessor__js_class : (StackTrace.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/exception/StackTrace"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), StackTrace.generated156(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class Stream extends FlatObject {
	iterator = flat_null;
	static log = flat_null;

	static construct(iterator) {
		let __value = new Stream();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Stream.__assignments.apply(__value, [].slice.call(arguments));
		__value = Stream.__init.call(__value, iterator);

		return __value;
	}

	destroy() {
	}

	static __init(iterator) {
		let self = this;

		this.iterator = iterator;
		return self;
	}

	map(mapFunc) {
		return Stream.construct(MapIterator.construct(this.iterator, mapFunc));
	}

	mapNotNull(mapFunc) {
		let self = this;

		return this.map(mapFunc).filter((_x) => {
				return _x !== flat_null;
		});
	}

	peek(func) {
		return Stream.construct(PeekIterator.construct(this.iterator, func));
	}

	flatMap(mapFunc) {
		return Stream.construct(FlatMapIterator.construct(this.iterator, mapFunc));
	}

	filter(filterFunc) {
		return Stream.construct(FilterIterator.construct(this.iterator, filterFunc));
	}

	filterNot(filterFunc) {
		return Stream.construct(FilterNotIterator.construct(this.iterator, filterFunc));
	}

	unique() {
		return Stream.construct(UniqueIterator.construct(this.iterator));
	}

	skip(count) {
		return Stream.construct(SkipIterator.construct(this.iterator, count));
	}

	take(count) {
		return Stream.construct(TakeIterator.construct(this.iterator, count));
	}

	firstWhere(func) {
		return this.filter(func).accessor_first();
	}

	firstOr(_js_default) {
		return this.iterator.accessor_hasNext() ? this.iterator.accessor_stepNext() : _js_default;
	}

	countWhere(func) {
		return this.filter(func).accessor_count();
	}

	any(func) {
		return this.filter(func).iterator.accessor_hasNext();
	}

	all(func) {
		return !this.filterNot(func).iterator.accessor_hasNext();
	}

	none(func) {
		return !this.filter(func).iterator.accessor_hasNext();
	}

	count(func) {
		return this.filter(func).accessor_count();
	}

	toArray() {
		let flat_local_0 = flat_null;
		let array = FlatArray.construct();
		flat_local_0 = this.iterator;
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			array.add0(value);
		}
		return array;
	}

	toSet() {
		let flat_local_0 = flat_null;
		let set = HashSet.construct0(undefined, undefined);
		flat_local_0 = this.iterator;
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			set.add(value);
		}
		return set;
	}

	forEach(func) {
		let flat_local_0 = flat_null;
		flat_local_0 = this.iterator;
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			func(value);
		}
	}

	async forEachParallel(func, maxParallel, onAfterChunk) {
		let self = this;

		maxParallel = typeof maxParallel === 'undefined' ? -1 : maxParallel;
		onAfterChunk = typeof onAfterChunk === 'undefined' ? (_count) => {
		} : onAfterChunk;
		(await Async.all1(this.toArray(), async (_1, _2) => {
					(await func(_1));
					return flat_null;
		}, maxParallel, onAfterChunk));
	}

	async forEachAsync(func) {
		let flat_local_0 = flat_null;
		flat_local_0 = this.iterator;
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			(await func(value));
		}
	}

	minBy(func) {
		let flat_local_0 = flat_null;
		let min = FlatLong.MAX_VALUE;
		flat_local_0 = this.iterator;
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			min = FlatMath.min0(min, func(value));
		}
		return min;
	}

	maxBy(func) {
		let flat_local_0 = flat_null;
		let max = FlatLong.MIN_VALUE;
		flat_local_0 = this.iterator;
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			max = FlatMath.max0(max, func(value));
		}
		return max;
	}

	sumBy(func) {
		let flat_local_0 = flat_null;
		let sum = 0;
		flat_local_0 = this.iterator;
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			sum += func(value);
		}
		return sum;
	}

	meanBy(func) {
		let flat_local_0 = flat_null;
		let sum = 0;
		let count = 0;
		flat_local_0 = this.iterator;
		while (flat_local_0.accessor_hasNext()) {
			let value = flat_null;
			value = flat_local_0.accessor_stepNext();
			sum += func(value);
			count++;
		}
		if (count === 0) {
			return 0;
		}
		return /*Double*//*Int*/sum / count;
	}

	join(delimiter, maxOutputCount, ellipsePosition, ellipse) {
		delimiter = typeof delimiter === 'undefined' ? FlatString.construct5("") : delimiter;
		maxOutputCount = typeof maxOutputCount === 'undefined' ? -1 : maxOutputCount;
		ellipsePosition = typeof ellipsePosition === 'undefined' ? ~~(maxOutputCount / 2) : ellipsePosition;
		ellipse = typeof ellipse === 'undefined' ? FlatString.construct5("...") : ellipse;
		if (maxOutputCount === 0 && this.iterator.accessor_hasNext()) {
			return ellipse;
		}
		let str = FlatString.construct5("");
		if (maxOutputCount === -1) {
			let flat_local_0 = flat_null;
			flat_local_0 = this.iterator;
			while (flat_local_0.accessor_hasNext()) {
				let value = flat_null;
				value = flat_local_0.accessor_stepNext();
				if (maxOutputCount === 0) {
					return ellipse;
				}
				if (str.accessor_isNotEmpty()) {
					str = str.concat(delimiter);
				}
				str = str.concat(value.toString());
			}
		} else {
			let flat_local_1 = flat_null;
			let i = 0;
			let buffer = FlatArray.construct();
			flat_local_1 = this.iterator;
			while (flat_local_1.accessor_hasNext()) {
				let value = flat_null;
				value = flat_local_1.accessor_stepNext();
				if (i < ellipsePosition) {
					str = str.concat(value.toString().plus0(delimiter));
				} else if (i === ellipsePosition) {
					str = str.concat(ellipse);
				} else {
					buffer.add0(value.toString());
				}
				i++;
			}
			let start = buffer.accessor_count() - ellipsePosition - (~~(maxOutputCount % 2));
			{
				let j = start;
				for (; j < buffer.accessor_count(); j++) {
					str = str.concat(delimiter.plus0(buffer.get(j).toString()));
				}
			}
		}
		return str;
	}

	static generated136(value0, value1, value2, value3, value4, value5) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		return FlatArray.construct1(temp, 6);
	}

	static generated159(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		temp[14] = value14;
		temp[15] = value15;
		temp[16] = value16;
		temp[17] = value17;
		temp[18] = value18;
		temp[19] = value19;
		temp[20] = value20;
		temp[21] = value21;
		temp[22] = value22;
		temp[23] = value23;
		temp[24] = value24;
		temp[25] = value25;
		temp[26] = value26;
		temp[27] = value27;
		return FlatArray.construct1(temp, 28);
	}

	accessor_last() {
		let value = flat_null;
		while (this.iterator.accessor_hasNext()) {
			value = this.iterator.accessor_stepNext();
		}
		return value;
	}

	accessor_lastNonNull() {
		let value = flat_null;
		while (this.iterator.accessor_hasNext()) {
			let x = flat_null;
			if ((x = this.iterator.accessor_stepNext()) !== flat_null) {
				value = x;
			}
		}
		return value;
	}

	accessor_count() {
		let flat_local_0 = flat_null;
		let value = 0;
		flat_local_0 = this.iterator;
		while (flat_local_0.accessor_hasNext()) {
			let x = flat_null;
			x = flat_local_0.accessor_stepNext();
			value++;
		}
		return value;
	}

	static accessor__js_class() {
		return typeof Stream.__lazy_accessor__js_class !== 'undefined' ? Stream.__lazy_accessor__js_class : (Stream.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/stream/Stream"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Stream.generated136(Field.construct(FlatString.construct5("first")), Field.construct(FlatString.construct5("last")), Field.construct(FlatString.construct5("firstNonNull")), Field.construct(FlatString.construct5("lastNonNull")), Field.construct(FlatString.construct5("count")), Field.construct(FlatString.construct5("iterator"))), Stream.generated159(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("map")), Function.construct(FlatString.construct5("mapNotNull")), Function.construct(FlatString.construct5("peek")), Function.construct(FlatString.construct5("flatMap")), Function.construct(FlatString.construct5("filter")), Function.construct(FlatString.construct5("filterNot")), Function.construct(FlatString.construct5("unique")), Function.construct(FlatString.construct5("skip")), Function.construct(FlatString.construct5("take")), Function.construct(FlatString.construct5("firstWhere")), Function.construct(FlatString.construct5("firstOr")), Function.construct(FlatString.construct5("countWhere")), Function.construct(FlatString.construct5("any")), Function.construct(FlatString.construct5("all")), Function.construct(FlatString.construct5("none")), Function.construct(FlatString.construct5("count")), Function.construct(FlatString.construct5("toArray")), Function.construct(FlatString.construct5("toSet")), Function.construct(FlatString.construct5("forEach")), Function.construct(FlatString.construct5("forEachParallel")), Function.construct(FlatString.construct5("forEachAsync")), Function.construct(FlatString.construct5("minBy")), Function.construct(FlatString.construct5("maxBy")), Function.construct(FlatString.construct5("sumBy")), Function.construct(FlatString.construct5("meanBy")), Function.construct(FlatString.construct5("join"))), this);
			})());
	}

	accessor_first() {
		return this.iterator.accessor_hasNext() ? this.iterator.accessor_stepNext() : flat_null;
	}

	accessor_firstNonNull() {
		let self = this;

		return this.filter((_x) => {
				return _x !== flat_null;
		}).accessor_first();
	}

	static __assignments() {
	}
}

class StreamListExtensions {
	static List_stream() {
		return Stream.construct(this.accessor_iterator());
	}

	static generated99(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof StreamListExtensions.__lazy_accessor__js_class !== 'undefined' ? StreamListExtensions.__lazy_accessor__js_class : (StreamListExtensions.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/stream/StreamListExtensions"), false, flat_null, FlatArray.construct(), FlatArray.construct(), StreamListExtensions.generated99(Function.construct(FlatString.construct5("stream"))), this);
			})());
	}

	static __assignments() {
	}
}

class StreamReader extends FlatObject {
	static construct() {
		let __value = new StreamReader();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StreamReader.__assignments.apply(__value, [].slice.call(arguments));
		__value = StreamReader.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated132(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof StreamReader.__lazy_accessor__js_class !== 'undefined' ? StreamReader.__lazy_accessor__js_class : (StreamReader.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/StreamReader"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), StreamReader.generated132(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class StringBuilder extends FlatObject {
	value = flat_null;

	static construct(value) {
		let __value = new StringBuilder();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StringBuilder.__assignments.apply(__value, [].slice.call(arguments));
		__value = StringBuilder.__init.call(__value, value);

		return __value;
	}

	destroy() {
	}

	static __init(value) {
		let self = this;

		let flat_local_0 = flat_null;
		value = typeof value === 'undefined' ? FlatString.construct5("") : value;
		this.value = value;
		value = (flat_local_0 = value) !== flat_null ? flat_local_0 : FlatString.construct5("");
		return self;
	}

	write(str) {
		str = typeof str === 'undefined' ? FlatString.construct5("") : str;
		this.value = this.value.concat(str);
		return this;
	}

	toString() {
		return this.value;
	}

	static generated162(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated169(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static accessor__js_class() {
		return typeof StringBuilder.__lazy_accessor__js_class !== 'undefined' ? StringBuilder.__lazy_accessor__js_class : (StringBuilder.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/io/StringBuilder"), false, FlatObject.accessor__js_class(), StringBuilder.generated162(OutputStream.accessor__js_class()), FlatArray.construct(), StringBuilder.generated169(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("write")), Function.construct(FlatString.construct5("toString"))), this);
			})());
	}

	static __assignments() {
	}
}

class StringCharArray extends FlatObject {
	count = 0;
	data = flat_null;

	static construct0(count) {
		let __value = new StringCharArray();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StringCharArray.__assignments.apply(__value, [].slice.call(arguments));
		__value = StringCharArray.__init1.call(__value, count);

		return __value;
	}

	static construct1(data) {
		let __value = new StringCharArray();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StringCharArray.__assignments.apply(__value, [].slice.call(arguments));
		__value = StringCharArray.__init0.call(__value, data);

		return __value;
	}

	static construct2(data, count) {
		let __value = new StringCharArray();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StringCharArray.__assignments.apply(__value, [].slice.call(arguments));
		__value = StringCharArray.__init2.call(__value, data, count);

		return __value;
	}

	destroy() {
	}

	reverse() {
		let array = StringCharArray.construct0(this.count);
		{
			let i = 0;
			for (; i < this.count; i++) {
				array.set(this.count - i - 1, this.get(i));
			}
		}
		return array;
	}

	toCharArray() {
		let array = FlatArray.construct0(this.count, undefined);
		{
			let i = 0;
			for (; i < this.count; i++) {
				let c = this.data[i];
				array.set(i, Char.construct(c));
			}
		}
		return array;
	}

	toString() {
		return FlatString.construct1(this);
	}

	equals(other) {
		return ((other) !== flat_null || !((this) !== flat_null)) && other.accessor__js_class().isOfType(StringCharArray.accessor__js_class()) && this.equals0(other);
	}

	equals0(other) {
		if (this.count !== other.count) {
			return false;
		}
		if (this.data == null) return other.data == null;
		return this.data === other.data;
	}

	static __init0(data) {
		let self = this;

		this.count = data.accessor_count();
		this.data = data.data.join("");
		return self;
	}

	static __init1(count) {
		let self = this;

		let buf = flat_null;
		this.count = count;
		if (count == 0) {
			return "";
		}
		var count2 = count / 2;
		var result = '\0';
		while (result.length <= count2) {
			result += result;
		}
		buf = result + result.substring(0, count - result.length);
		this.data = buf;
		return self;
	}

	static __init2(data, count) {
		let self = this;

		this.data = data;
		this.count = count;
		this.data = typeof data === 'string' ? data : data.join("");
		return self;
	}

	get(index) {
		return this.data[index];
	}

	set(index, value) {
		this.data = this.data.substr(0, index) + value + this.data.substr(index + value.length);
		return value;
	}

	static generated167(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated174(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		return FlatArray.construct1(temp, 13);
	}

	static accessor__js_class() {
		return typeof StringCharArray.__lazy_accessor__js_class !== 'undefined' ? StringCharArray.__lazy_accessor__js_class : (StringCharArray.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/datastruct/list/StringCharArray"), false, FlatObject.accessor__js_class(), FlatArray.construct(), StringCharArray.generated167(Field.construct(FlatString.construct5("count")), Field.construct(FlatString.construct5("data")), Field.construct(FlatString.construct5("first")), Field.construct(FlatString.construct5("last"))), StringCharArray.generated174(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("reverse")), Function.construct(FlatString.construct5("toCharArray")), Function.construct(FlatString.construct5("toString")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("get")), Function.construct(FlatString.construct5("set"))), this);
			})());
	}

	accessor_first() {
		return this.count > 0 ? this.data[0] : 0;
	}

	accessor_last() {
		return this.count > 0 ? this.data[this.count - 1] : 0;
	}

	static __assignments() {
	}
}

class SyntaxStringFunctions {
	static EITHER_STATEMENT_END_CHARS = flat_null;
	static SYMBOLS_CHARS = flat_null;
	static ALL_SYMBOLS_CHARS = flat_null;
	static STMT_PRE_CONT_CHARS = flat_null;
	static STMT_POST_CONT_CHARS = flat_null;
	static INVALID_DECLARATION_CHARS = flat_null;
	static WORD_BOUNDARIES = flat_null;
	static WHITESPACE = flat_null;

	static String_isUnsignedNumber() {
		return this.__callExtension(RegexStringExtensions.String_matches, [Pattern.construct(FlatString.construct5("[0-9]+(\\.[0-9]+)?"))]);
	}

	static String_isUnsignedFloat() {
		if (this.count === 0) {
			return false;
		}
		{
			let i = 0;
			for (; i < this.count; i++) {
				let c = this.chars.get(i);
				switch (c) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
					continue;
					case '.':
					return this.__callExtension(SyntaxStringFunctions.String_isUnsignedInteger, [i]);
					default:
					return false;

				}
			}
		}
		return false;
	}

	static String_isUnsignedInteger(index) {
		index = typeof index === 'undefined' ? 0 : index;
		if (this.count === index) {
			return false;
		}
		while (index < this.count) {
			let c = this.chars.get(index++);
			switch (c) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				continue;
				default:
				return false;

			}
		}
		return true;
	}

	static String_isSymbol() {
		let self = this;

		return this.chars.toCharArray().all0((_x, _i, _list) => {
				return SyntaxStringFunctions.ALL_SYMBOLS_CHARS.contains0(_x);
		}, undefined);
	}

	static String_isCharLiteral() {
		return (this.count === 3 && this.accessor_first() === '\'' && this.accessor_last() === '\'') || (this.count === 4 && this.accessor_first() === '\'' && this.accessor_last() === '\'' && this.get(1) === '\\');
	}

	static String_containsAllWhitespaceAfter(index, direction) {
		direction = typeof direction === 'undefined' ? 1 : direction;
		{
			let i = index;
			for (; i < this.count && i >= 0; i += direction) {
				if (!SyntaxStringFunctions.WHITESPACE.contains0(Char.construct(this.chars.get(i)))) {
					return false;
				}
			}
		}
		return true;
	}

	static String_getArrayAccesses() {
		let array = flat_null;
		let bracketIndex = this.count - 1;
		while (this.chars.get(bracketIndex) === ']') {
			let start = this.__callExtension(SyntaxStringFunctions.String_findEndingMatch0, [bracketIndex, '[', ']', -1]);
			let old = bracketIndex;
			bracketIndex = start - 1;
			if (start > 0) {
				let flat_local_0 = flat_null;
				if (this.chars.get(bracketIndex) === ')' || this.chars.get(bracketIndex) === ']' || this.substring(undefined, start).accessor_isIdentifier()) {
				array = (flat_local_0 = array) !== flat_null ? flat_local_0 : FlatArray.construct();
				array.add1(0, this.substring(start + 1, old).trim(undefined, undefined, undefined));
			} else if ((array) !== flat_null) {
				array = flat_null;
			}
		}
		if (bracketIndex <= 1 || this.chars.get(bracketIndex) !== ']') {
			break;
		}
	}
	return Pair.construct(array, FlatInt.construct(bracketIndex + 1));
}

static String_containsWord(search, start) {
	start = typeof start === 'undefined' ? 0 : start;
	return this.__callExtension(SyntaxStringFunctions.String_nextWordIndex, [search, start]) >= 0;
}

static String_nextWordIndex(search, start, defaultReturnValue) {
	start = typeof start === 'undefined' ? 0 : start;
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
	let index = this.indexOf2(search, start, undefined, undefined);
	while (index >= 0) {
		if ((index === 0 || SyntaxStringFunctions.WHITESPACE.contains0(Char.construct(this.chars.get(index - 1)))) && (index + search.count >= this.count || SyntaxStringFunctions.WHITESPACE.contains0(Char.construct(this.chars.get(index + search.count))))) {
			return index;
		}
		index = this.indexOf2(search, index + search.count + 1, undefined, undefined);
	}
	return defaultReturnValue;
}

static String_nextWord(start, direction, defaultReturnValue) {
	start = typeof start === 'undefined' ? 0 : start;
	direction = typeof direction === 'undefined' ? 1 : direction;
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? flat_null : defaultReturnValue;
	let wordStartIndex = this.__callExtension(SyntaxStringFunctions.String_nextNonWhitespaceIndex, [start, direction]);
	if (wordStartIndex >= 0) {
		let wordEndIndex = this.__callExtension(SyntaxStringFunctions.String_nextWhitespaceIndex, [wordStartIndex, direction]);
		if (direction < 0) {
			let temp = wordStartIndex + 1;
			wordStartIndex = wordEndIndex + 1;
			wordEndIndex = temp;
		}
		return this.substring(wordStartIndex, wordEndIndex);
	}
	return defaultReturnValue;
}

static String_nextNonWhitespaceChar(start, defaultReturnValue) {
	start = typeof start === 'undefined' ? 0 : start;
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? 0 : defaultReturnValue;
	let index = this.__callExtension(SyntaxStringFunctions.String_nextNonWhitespaceIndex, [start]);
	if (index >= 0) {
		return this.chars.get(index);
	}
	return defaultReturnValue;
}

static String_nextNonWhitespaceIndex(start, direction, defaultReturnValue) {
	start = typeof start === 'undefined' ? 0 : start;
	direction = typeof direction === 'undefined' ? 1 : direction;
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
	return this.__callExtension(SyntaxStringFunctions.String_nextIndexThatContains, [SyntaxStringFunctions.WHITESPACE, start, true, direction, defaultReturnValue]);
}

static String_nextWhitespaceIndex(start, direction, defaultReturnValue) {
	start = typeof start === 'undefined' ? 0 : start;
	direction = typeof direction === 'undefined' ? 1 : direction;
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
	return this.__callExtension(SyntaxStringFunctions.String_nextIndexThatContains, [SyntaxStringFunctions.WHITESPACE, start, false, direction, defaultReturnValue]);
}

static String_nextIndexThatDoesntContain(searchChars, start, direction, defaultReturnValue) {
	start = typeof start === 'undefined' ? 0 : start;
	direction = typeof direction === 'undefined' ? 1 : direction;
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
	return this.__callExtension(SyntaxStringFunctions.String_nextIndexThatContains, [searchChars, start, true, direction, defaultReturnValue]);
}

static String_nextIndexThatContains(searchChars, start, opposite, direction, defaultReturnValue) {
	start = typeof start === 'undefined' ? 0 : start;
	opposite = typeof opposite === 'undefined' ? false : opposite;
	direction = typeof direction === 'undefined' ? 1 : direction;
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
	while (start < this.count && start >= 0 && searchChars.contains0(Char.construct(this.chars.get(start))) === opposite) {
		start += direction;
	}
	if (start <= this.count && start >= 0) {
		return start;
	}
	return defaultReturnValue;
}

static String_nextLetterIndex(start, direction, opposite, bound) {
	start = typeof start === 'undefined' ? 0 : start;
	direction = typeof direction === 'undefined' ? 1 : direction;
	opposite = typeof opposite === 'undefined' ? false : opposite;
	bound = typeof bound === 'undefined' ? false : bound;
	while (start >= 0 && start < this.count) {
		let c = this.chars.get(start);
		if ((SyntaxStringFunctions.WHITESPACE.contains0(Char.construct(c)) || SyntaxStringFunctions.SYMBOLS_CHARS.contains0(Char.construct(c))) === opposite) {
			return start;
		}
		start += direction;
	}
	if (bound) {
		if (direction > 0) {
			return this.count;
		}
		return 0;
	}
	return -1;
}

static String_isSurroundedByQuotes() {
	return this.__callExtension(SyntaxStringFunctions.String_isSurroundedByChars, ['"', '"']);
}

static String_removeSurroundingQuotes() {
	return this.__callExtension(SyntaxStringFunctions.String_removeSurroundingChars, ['"', '"']);
}

static String_isSurroundedByBrackets() {
	return this.__callExtension(SyntaxStringFunctions.String_isSurroundedByChars, ['[', ']']);
}

static String_removeSurroundingBrackets() {
	return this.__callExtension(SyntaxStringFunctions.String_removeSurroundingChars, ['[', ']']);
}

static String_isSurroundedByChars(beginning, ending) {
	return this.count >= 2 && this.chars.accessor_first() === beginning && this.chars.accessor_last() === ending;
}

static String_removeSurroundingChars(beginning, ending) {
	let input = this;
	while (input.__callExtension(SyntaxStringFunctions.String_isSurroundedByChars, [beginning, ending])) {
		input = input.substring(1, input.count - 1);
	}
	return input;
}

static String_containsString0(needles, index) {
	let self = this;

	index = typeof index === 'undefined' ? 0 : index;
	return needles.any0((_x, _i, _list) => {
			return self.__callExtension(SyntaxStringFunctions.String_containsString1, [_x, index]);
	});
}

static String_containsString1(needle, index) {
	index = typeof index === 'undefined' ? 0 : index;
	{
		let i = 0;
		for (; i < needle.count; i++) {
			if (i + index >= this.count || this.chars.get(i + index) !== needle.get(i)) {
				return false;
			}
		}
	}
	return true;
}

static isCharacterEscaped(value, start) {
	if (start <= 0) {
		return false;
	}
	if (value.get(start - 1) !== '\\') {
		return false;
	}
	return !SyntaxStringFunctions.isCharacterEscaped(value, start - 1);
}

static defaultCharacterCheck(value, char, startI, directionI) {
	directionI = typeof directionI === 'undefined' ? 1 : directionI;
	startI += directionI;
	while (SyntaxStringFunctions.isCharacterEscaped(value, startI)) {
		startI = startI + directionI;
	}
	return startI;
}

static String_findEndingChar(c, start, direction, advance, defaultReturnValue) {
	direction = typeof direction === 'undefined' ? 1 : direction;
	advance = typeof advance === 'undefined' ? SyntaxStringFunctions.defaultCharacterCheck : advance;
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
	if (start < 0 || start >= this.count) {
		return -1;
	}
	start = advance(this, this.chars.get(start), start, direction);
	while (start >= 0 && start < this.count) {
		if (this.chars.get(start) === c) {
			return start;
		}
		start = advance(this, this.chars.get(start), start, direction);
	}
	return defaultReturnValue;
}

static String_findEndingQuote(start, direction) {
	direction = typeof direction === 'undefined' ? 1 : direction;
	return this.__callExtension(SyntaxStringFunctions.String_findEndingStringChar, ['"', start, direction]);
}

static String_findEndingSingleQuote(start, direction) {
	direction = typeof direction === 'undefined' ? 1 : direction;
	return this.__callExtension(SyntaxStringFunctions.String_findEndingStringChar, ['\'', start, direction]);
}

static String_findEndingStringChar(c, start, direction) {
	let self = this;

	direction = typeof direction === 'undefined' ? 1 : direction;
	return this.__callExtension(SyntaxStringFunctions.String_findEndingChar, [c, start, direction, (str, char, i, dir) => {
			i += dir;
			if (dir > 0 && i < str.count - 3 && str.get(i) === '#' && str.get(i + 1) === '{') {
				return (i = str.__callExtension(SyntaxStringFunctions.String_findEndingChar, ['}', i + dir, dir])) >= 0 ? i + dir : i;
		}
		return SyntaxStringFunctions.defaultCharacterCheck(str, char, i - dir, dir);
}]);}

static String_findEndingMatch0(index, start, end, direction, escapeChar) {
	direction = typeof direction === 'undefined' ? 1 : direction;
	escapeChar = typeof escapeChar === 'undefined' ? '\0' : escapeChar;
	return this.__callExtension(SyntaxStringFunctions.String_findEndingMatch1, [index, Char.toString((start)).plus0(FlatString.construct5("")), Char.toString((end)).plus0(FlatString.construct5("")), direction, escapeChar]);
}

static String_findEndingMatch1(index, start, end, direction, escapeChar, defaultReturnValue) {
	direction = typeof direction === 'undefined' ? 1 : direction;
	escapeChar = typeof escapeChar === 'undefined' ? '\0' : escapeChar;
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
	if (direction < 0) {
		let temp = start;
		start = end;
		end = temp;
	}
	let scope = 0;
	while (index >= 0 && index < this.count) {
		let c = this.chars.get(index);
		if (c === escapeChar && direction > 0) {
			if (index < this.count - 1) {
				if (this.__callExtension(SyntaxStringFunctions.String_containsString1, [start, index + 1])) {
					index++;
				}
			}
		} else if (this.__callExtension(SyntaxStringFunctions.String_containsString1, [start, index]) && (direction > 0 || index > 0 && this.chars.get(index - 1) !== escapeChar) && (!start.equals0(end) || scope === 0)) {
			scope++;
		} else if (this.__callExtension(SyntaxStringFunctions.String_containsString1, [end, index]) && (direction > 0 || index > 0 && this.chars.get(index - 1) !== escapeChar)) {
			scope--;
			if (scope === 0) {
				return index;
			}
		} else if (c === '"') {
			index = this.__callExtension(SyntaxStringFunctions.String_findEndingQuote, [index, direction]);
			if (index < 0) {
				break;
			}
		} else if (c === '\'') {
			index = this.__callExtension(SyntaxStringFunctions.String_findEndingSingleQuote, [index, direction]);
			if (index < 0) {
				break;
			}
		}
		index += direction;
	}
	return defaultReturnValue;
}

static String_splitAtDotOperator(defaultReturnValue) {
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? flat_null : defaultReturnValue;
	let prev = 0;
	let dot = this.__callExtension(SyntaxStringFunctions.String_findDotOperatorIndex, []);
	let fragments = FlatArray.construct();
	while (dot > 0) {
		fragments.add0(this.substring(prev, dot));
		prev = dot + 1;
		dot = this.__callExtension(SyntaxStringFunctions.String_findDotOperatorIndex, [prev]);
	}
	if (prev > 0) {
		fragments.add0(this.substring(prev, undefined));
		return fragments;
	} else {
		return defaultReturnValue;
	}
}

static String_splitValues() {
	return this.__callExtension(RegexStringExtensions.String_split, [Pattern.construct(FlatString.construct5("\\s+"))]);
}

static String_splitAtCommas(searchGenerics, allowTrailing) {
	searchGenerics = typeof searchGenerics === 'undefined' ? false : searchGenerics;
	allowTrailing = typeof allowTrailing === 'undefined' ? false : allowTrailing;
	return this.__callExtension(SyntaxStringFunctions.String_splitOnTopLevel0, [',', searchGenerics, allowTrailing]);
}

static String_splitOnTopLevel0(c, searchGenerics, allowTrailing) {
	searchGenerics = typeof searchGenerics === 'undefined' ? false : searchGenerics;
	allowTrailing = typeof allowTrailing === 'undefined' ? false : allowTrailing;
	return this.__callExtension(SyntaxStringFunctions.String_splitOnTopLevel1, [Char.toString(c), searchGenerics, allowTrailing]);
}

static String_splitOnTopLevel1(str, searchGenerics, allowTrailing) {
	searchGenerics = typeof searchGenerics === 'undefined' ? false : searchGenerics;
	allowTrailing = typeof allowTrailing === 'undefined' ? false : allowTrailing;
	let strs = FlatArray.construct();
	if (this.trim(undefined, undefined, undefined).count === 0) {
		return strs;
	}
	let oldIndex = 0;
	let index = -1;
	while ((index = this.__callExtension(SyntaxStringFunctions.String_findStringOnTopLevel0, [str, index + 1, searchGenerics])) >= 0) {
		strs.add0(this.substring(oldIndex, index).trim(undefined, undefined, undefined));
		oldIndex = index + str.count;
	}
	let last = this.substring(oldIndex, undefined).trim(undefined, undefined, undefined);
	if (!allowTrailing || last.count > 0) {
		strs.add0(last);
	}
	return strs;
}

static String_splitWhitespace(searchGenerics, allowTrailing) {
	searchGenerics = typeof searchGenerics === 'undefined' ? false : searchGenerics;
	allowTrailing = typeof allowTrailing === 'undefined' ? false : allowTrailing;
	return this.__callExtension(SyntaxStringFunctions.String_splitOnTopLevel2, [SyntaxStringFunctions.WHITESPACE, searchGenerics, allowTrailing]);
}

static String_splitOnTopLevel2(chars, searchGenerics, allowTrailing) {
	searchGenerics = typeof searchGenerics === 'undefined' ? false : searchGenerics;
	allowTrailing = typeof allowTrailing === 'undefined' ? false : allowTrailing;
	let strs = FlatArray.construct();
	if (this.trim(undefined, undefined, undefined).count === 0) {
		return strs;
	}
	let oldIndex = 0;
	let index = -1;
	while ((index = this.__callExtension(SyntaxStringFunctions.String_findCharOnTopLevel1, [chars, index + 1, searchGenerics])) >= 0) {
		strs.add0(this.substring(oldIndex, index).trim(undefined, undefined, undefined));
		oldIndex = index + 1;
	}
	let last = this.substring(oldIndex, undefined).trim(undefined, undefined, undefined);
	if (!allowTrailing || last.count > 0) {
		strs.add0(last);
	}
	return strs;
}

static String_findLastWhitespaceOnTopLevel(start, searchGenerics) {
	start = typeof start === 'undefined' ? this.count - 1 : start;
	searchGenerics = typeof searchGenerics === 'undefined' ? false : searchGenerics;
	return this.__callExtension(SyntaxStringFunctions.String_findWhitespaceOnTopLevel, [start, searchGenerics, -1]);
}

static String_findWhitespaceOnTopLevel(start, searchGenerics, step) {
	start = typeof start === 'undefined' ? 0 : start;
	searchGenerics = typeof searchGenerics === 'undefined' ? false : searchGenerics;
	step = typeof step === 'undefined' ? 1 : step;
	return this.__callExtension(SyntaxStringFunctions.String_findCharOnTopLevel1, [SyntaxStringFunctions.WHITESPACE, start, searchGenerics, step]);
}

static String_findCharOnTopLevel0(needle, start, searchGenerics, step) {
	start = typeof start === 'undefined' ? 0 : start;
	searchGenerics = typeof searchGenerics === 'undefined' ? false : searchGenerics;
	step = typeof step === 'undefined' ? 1 : step;
	let array = FlatArray.construct0(1, undefined);
	array.add0(Char.construct(needle));
	return this.__callExtension(SyntaxStringFunctions.String_findCharOnTopLevel1, [array, start, searchGenerics, step]);
}

static String_findCharOnTopLevel1(needles, start, searchGenerics, step, defaultReturnValue) {
	let self = this;

	start = typeof start === 'undefined' ? 0 : start;
	searchGenerics = typeof searchGenerics === 'undefined' ? false : searchGenerics;
	step = typeof step === 'undefined' ? 1 : step;
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
	let index = start;
	while (index < this.count && index >= 0) {
		let c = this.chars.get(index);
		if (needles.any0((_x, _i, _list) => {
					let flat_local_0 = flat_null;
					return ((flat_local_0 = (_x)) !== flat_null ? (flat_local_0.value) : 0) === c;
		})) {
			return index;
		} else {
			switch ((c)) {
				case '"':
				index = this.__callExtension(SyntaxStringFunctions.String_findEndingQuote, [index, step]) + step;
				break;
				case '\'':
				index = this.__callExtension(SyntaxStringFunctions.String_findEndingSingleQuote, [index, step]) + step;
				break;
				case '(':
				index = this.__callExtension(SyntaxStringFunctions.String_findEndingMatch0, [index, '(', ')', step]) + step;
			break;
			case '{':
			index = this.__callExtension(SyntaxStringFunctions.String_findEndingMatch0, [index, '{', '}', step]) + step;
		break;
		case '[':
		index = this.__callExtension(SyntaxStringFunctions.String_findEndingMatch0, [index, '[', ']', step]) + step;
		break;
		case '<':
		if (searchGenerics) {
			index = this.__callExtension(SyntaxStringFunctions.String_findEndingMatch0, [index, '<', '>', step]);
		}
		default:
		index += step;

	}
}}
return defaultReturnValue;}

static String_findWordOnTopLevel(word, start, searchGenerics, step, defaultReturnValue) {
	start = typeof start === 'undefined' ? 0 : start;
	searchGenerics = typeof searchGenerics === 'undefined' ? false : searchGenerics;
	step = typeof step === 'undefined' ? 1 : step;
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
	let index = this.__callExtension(SyntaxStringFunctions.String_findStringOnTopLevel0, [word, start, searchGenerics, defaultReturnValue]);
	while (index >= 0 && ((index > 0 && !SyntaxStringFunctions.WORD_BOUNDARIES.contains0(Char.construct(this.chars.get(index - 1)))) || (index + word.count < this.count - 1 && !SyntaxStringFunctions.WORD_BOUNDARIES.contains0(Char.construct(this.chars.get(index + word.count)))))) {
		index = this.__callExtension(SyntaxStringFunctions.String_findStringOnTopLevel0, [word, index + word.count + 1, searchGenerics, step, defaultReturnValue]);
	}
	return index;
}

static String_findStringOnTopLevel0(needle, start, searchGenerics, step, defaultReturnValue) {
	start = typeof start === 'undefined' ? 0 : start;
	searchGenerics = typeof searchGenerics === 'undefined' ? false : searchGenerics;
	step = typeof step === 'undefined' ? 1 : step;
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
	return this.__callExtension(SyntaxStringFunctions.String_findStringOnTopLevel1, [SyntaxStringFunctions.generated263(needle), start, searchGenerics, step, defaultReturnValue]);
}

static String_findStringOnTopLevel1(needles, start, searchGenerics, step, defaultReturnValue) {
	start = typeof start === 'undefined' ? 0 : start;
	searchGenerics = typeof searchGenerics === 'undefined' ? false : searchGenerics;
	step = typeof step === 'undefined' ? 1 : step;
	defaultReturnValue = typeof defaultReturnValue === 'undefined' ? -1 : defaultReturnValue;
	let index = start;
	while (index < this.count && index >= 0) {
		let c = this.chars.get(index);
		if (this.__callExtension(SyntaxStringFunctions.String_containsString0, [needles, index])) {
			return index;
		} else {
			switch ((c)) {
				case '"':
				index = this.__callExtension(SyntaxStringFunctions.String_findEndingQuote, [index, step]) + step;
				break;
				case '\'':
				index = this.__callExtension(SyntaxStringFunctions.String_findEndingSingleQuote, [index, step]) + step;
				break;
				case '(':
				index = this.__callExtension(SyntaxStringFunctions.String_findEndingMatch0, [index, '(', ')', step]) + step;
			break;
			case '{':
			index = this.__callExtension(SyntaxStringFunctions.String_findEndingMatch0, [index, '{', '}', step]) + step;
		break;
		case '[':
		index = this.__callExtension(SyntaxStringFunctions.String_findEndingMatch0, [index, '[', ']', step]) + step;
		break;
		case '<':
		if (searchGenerics) {
			index = this.__callExtension(SyntaxStringFunctions.String_findEndingMatch0, [index, '<', '>', step]);
		}
		default:
		index += step;

	}
}}
return defaultReturnValue;}

static String_findGroupedSymbols(start, direction) {
	start = typeof start === 'undefined' ? 0 : start;
	direction = typeof direction === 'undefined' ? 1 : direction;
	return this.__callExtension(SyntaxStringFunctions.String_findGroupedChars, [SyntaxStringFunctions.SYMBOLS_CHARS, start, direction]);
}

static String_findGroupedChars(chars, start, direction) {
	start = typeof start === 'undefined' ? 0 : start;
	direction = typeof direction === 'undefined' ? 1 : direction;
	let index = start;
	while (index < this.count && index >= 0 && chars.contains0(Char.construct(this.chars.get(index)))) {
		index += direction;
	}
	if (direction < 0) {
		return this.substring(index + 1, start + 1);
	}
	return this.substring(start, index);
}

static String_searchGenericType(start, backwards) {
	backwards = typeof backwards === 'undefined' ? true : backwards;
	if (backwards) {
		let stack = 0;
		let index = 0;
		{
			let i = start;
			for (; i >= 0; i--) {
				let c = this.chars.get(i);
				if (c === '>') {
					index = stack === 0 ? i : index;
					stack++;
				} else if (c === '<') {
					stack--;
				}
				if (stack === 0) {
					if (index > 0) {
						return this.substring(i + 1, index);
					}
					return flat_null;
				}
			}
		}
	} else {
	}
	return flat_null;
}

static String_findDotOperatorIndex(start) {
	start = typeof start === 'undefined' ? 0 : start;
	return this.__callExtension(SyntaxStringFunctions.String_findCharOnTopLevel0, ['.', start]);
}

static generated150(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9) {
	let temp = flat_null;
	temp = [];
	temp[0] = value0;
	temp[1] = value1;
	temp[2] = value2;
	temp[3] = value3;
	temp[4] = value4;
	temp[5] = value5;
	temp[6] = value6;
	temp[7] = value7;
	temp[8] = value8;
	temp[9] = value9;
	return FlatArray.construct1(temp, 10);
}

static generated182(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22, value23, value24, value25, value26, value27, value28, value29, value30, value31, value32, value33, value34, value35, value36, value37, value38, value39, value40, value41, value42, value43, value44, value45, value46, value47, value48, value49) {
	let temp = flat_null;
	temp = [];
	temp[0] = value0;
	temp[1] = value1;
	temp[2] = value2;
	temp[3] = value3;
	temp[4] = value4;
	temp[5] = value5;
	temp[6] = value6;
	temp[7] = value7;
	temp[8] = value8;
	temp[9] = value9;
	temp[10] = value10;
	temp[11] = value11;
	temp[12] = value12;
	temp[13] = value13;
	temp[14] = value14;
	temp[15] = value15;
	temp[16] = value16;
	temp[17] = value17;
	temp[18] = value18;
	temp[19] = value19;
	temp[20] = value20;
	temp[21] = value21;
	temp[22] = value22;
	temp[23] = value23;
	temp[24] = value24;
	temp[25] = value25;
	temp[26] = value26;
	temp[27] = value27;
	temp[28] = value28;
	temp[29] = value29;
	temp[30] = value30;
	temp[31] = value31;
	temp[32] = value32;
	temp[33] = value33;
	temp[34] = value34;
	temp[35] = value35;
	temp[36] = value36;
	temp[37] = value37;
	temp[38] = value38;
	temp[39] = value39;
	temp[40] = value40;
	temp[41] = value41;
	temp[42] = value42;
	temp[43] = value43;
	temp[44] = value44;
	temp[45] = value45;
	temp[46] = value46;
	temp[47] = value47;
	temp[48] = value48;
	temp[49] = value49;
	return FlatArray.construct1(temp, 50);
}

static generated263(value0) {
	let temp = flat_null;
	temp = [];
	temp[0] = value0;
	return FlatArray.construct1(temp, 1);
}

static accessor__js_class() {
	return typeof SyntaxStringFunctions.__lazy_accessor__js_class !== 'undefined' ? SyntaxStringFunctions.__lazy_accessor__js_class : (SyntaxStringFunctions.__lazy_accessor__js_class = (() => {
				return Class.construct1(FlatString.construct5("flat/extensions/SyntaxStringFunctions"), false, flat_null, FlatArray.construct(), SyntaxStringFunctions.generated150(Field.construct(FlatString.construct5("isIdentifier")), Field.construct(FlatString.construct5("isNumber")), Field.construct(FlatString.construct5("isFloat")), Field.construct(FlatString.construct5("isInteger")), Field.construct(FlatString.construct5("isUnsignedNumber")), Field.construct(FlatString.construct5("isUnsignedFloat")), Field.construct(FlatString.construct5("isUnsignedInteger")), Field.construct(FlatString.construct5("isBool")), Field.construct(FlatString.construct5("firstWord")), Field.construct(FlatString.construct5("WHITESPACE"))), SyntaxStringFunctions.generated182(Function.construct(FlatString.construct5("isUnsignedNumber")), Function.construct(FlatString.construct5("isUnsignedFloat")), Function.construct(FlatString.construct5("isUnsignedInteger")), Function.construct(FlatString.construct5("isSymbol")), Function.construct(FlatString.construct5("isCharLiteral")), Function.construct(FlatString.construct5("containsAllWhitespaceAfter")), Function.construct(FlatString.construct5("getArrayAccesses")), Function.construct(FlatString.construct5("containsWord")), Function.construct(FlatString.construct5("nextWordIndex")), Function.construct(FlatString.construct5("nextWord")), Function.construct(FlatString.construct5("nextNonWhitespaceChar")), Function.construct(FlatString.construct5("nextNonWhitespaceIndex")), Function.construct(FlatString.construct5("nextWhitespaceIndex")), Function.construct(FlatString.construct5("nextIndexThatDoesntContain")), Function.construct(FlatString.construct5("nextIndexThatContains")), Function.construct(FlatString.construct5("nextLetterIndex")), Function.construct(FlatString.construct5("isSurroundedByQuotes")), Function.construct(FlatString.construct5("removeSurroundingQuotes")), Function.construct(FlatString.construct5("isSurroundedByBrackets")), Function.construct(FlatString.construct5("removeSurroundingBrackets")), Function.construct(FlatString.construct5("isSurroundedByChars")), Function.construct(FlatString.construct5("removeSurroundingChars")), Function.construct(FlatString.construct5("containsString")), Function.construct(FlatString.construct5("containsString")), Function.construct(FlatString.construct5("isCharacterEscaped")), Function.construct(FlatString.construct5("defaultCharacterCheck")), Function.construct(FlatString.construct5("findEndingChar")), Function.construct(FlatString.construct5("findEndingQuote")), Function.construct(FlatString.construct5("findEndingSingleQuote")), Function.construct(FlatString.construct5("findEndingStringChar")), Function.construct(FlatString.construct5("findEndingMatch")), Function.construct(FlatString.construct5("findEndingMatch")), Function.construct(FlatString.construct5("splitAtDotOperator")), Function.construct(FlatString.construct5("splitValues")), Function.construct(FlatString.construct5("splitAtCommas")), Function.construct(FlatString.construct5("splitOnTopLevel")), Function.construct(FlatString.construct5("splitOnTopLevel")), Function.construct(FlatString.construct5("splitWhitespace")), Function.construct(FlatString.construct5("splitOnTopLevel")), Function.construct(FlatString.construct5("findLastWhitespaceOnTopLevel")), Function.construct(FlatString.construct5("findWhitespaceOnTopLevel")), Function.construct(FlatString.construct5("findCharOnTopLevel")), Function.construct(FlatString.construct5("findCharOnTopLevel")), Function.construct(FlatString.construct5("findWordOnTopLevel")), Function.construct(FlatString.construct5("findStringOnTopLevel")), Function.construct(FlatString.construct5("findStringOnTopLevel")), Function.construct(FlatString.construct5("findGroupedSymbols")), Function.construct(FlatString.construct5("findGroupedChars")), Function.construct(FlatString.construct5("searchGenericType")), Function.construct(FlatString.construct5("findDotOperatorIndex"))), this);
		})());
}

accessor_isIdentifier() {
	return this.count > 0 && this.__callExtension(RegexStringExtensions.String_matches, [Pattern.construct(FlatString.construct5("[A-Za-z_][A-Za-z0-9_]*"))]);
}

accessor_isNumber() {
	return this.count > 0 && this.__callExtension(RegexStringExtensions.String_matches, [Pattern.construct(FlatString.construct5("-?[0-9]+(\\.[0-9]+)?"))]);
}

accessor_isFloat() {
	return this.count > 0 && this.__callExtension(RegexStringExtensions.String_matches, [Pattern.construct(FlatString.construct5("-?[0-9]+\\.[0-9]+"))]);
}

accessor_isInteger() {
	return this.count > 0 && this.__callExtension(RegexStringExtensions.String_matches, [Pattern.construct(FlatString.construct5("-?[0-9]+"))]);
}

accessor_isUnsignedNumber() {
	return this.__callExtension(RegexStringExtensions.String_matches, [Pattern.construct(FlatString.construct5("[0-9]+(\\.[0-9]+)?"))]);
}

accessor_isUnsignedFloat() {
	return this.count > 0 && this.__callExtension(RegexStringExtensions.String_matches, [Pattern.construct(FlatString.construct5("[0-9]+\\.[0-9]+"))]);
}

accessor_isUnsignedInteger() {
	return this.count > 0 && this.__callExtension(RegexStringExtensions.String_matches, [Pattern.construct(FlatString.construct5("[0-9]+"))]);
}

accessor_isBool() {
	return this.equals(FlatString.construct5("true")) || this.equals(FlatString.construct5("false"));
}

accessor_firstWord() {
	return this.__callExtension(SyntaxStringFunctions.String_nextWord, []);
}

static __assignments() {
}}

class System extends FlatObject {
	static TARGET = flat_null;
	static OS = flat_null;
	static WINDOWS = 0;
	static LINUX = 0;
	static MAC_OSX = 0;
	static ANDROID = 0;
	static IOS = 0;
	static UNKNOWN = 0;
	static OS_INT = 0;
	static overheadTimer = flat_null;

	static construct() {
		let __value = new System();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		System.__assignments.apply(__value, [].slice.call(arguments));
		__value = System.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static exit0(code) {
		process.exit(code);
	}

	static exit1(code, message, log) {
		log = typeof log === 'undefined' ? false : log;
		if (log) {
			let file = File.construct1(FlatString.construct5("Log").plus0(FlatLong.toString(Time.accessor_currentTimeMillis()).plus0(FlatString.construct5(".txt"))));
			let f = FileWriter.construct0(file);
			f.writeLine(message);
		}
		FlatConsole.writeLine1(message);
		System.exit0(code);
	}

	static runMain(argc, argvs, mainFunc, initialize, callStaticBlocks) {
		let args = flat_null;
		initialize = typeof initialize === 'undefined' ? () => {
		} : initialize;
		callStaticBlocks = typeof callStaticBlocks === 'undefined' ? () => {
		} : callStaticBlocks;

		initialize();
		try {
			callStaticBlocks();
			args = FlatArray.construct0(argc, undefined);
			{
				let i = 0;
				for (; i < argc; i++) {
					let allocated = flat_null;
					args.set(i, FlatString.construct4(allocated));
				}
			}
			mainFunc(args);
		} catch (e)  {
			if (!(e instanceof Exception)) {
				console.log(0.8661583960055848);
				console.error(e);
				process.exit(1);
			} else {
				let message = (e.accessor__js_class().location).plus0(FlatString.construct5(" in main Thread"));
				if ((e.message) !== flat_null) {
					message = message.concat(FlatString.construct5(": ").plus0((e.message).plus0(FlatString.construct5(""))));
				}
				FlatConsole.log(message);
			}
		 }
		finally {
		}

		return 0;
	}

	static jsCreateInstance(className, overloadId) {
		overloadId = typeof overloadId === 'undefined' ? -1 : overloadId;
		let instance = flat_null;
		let constructorName = FlatString.construct5("new").plus0((className).plus0(FlatString.construct5("")));
		if (overloadId >= 0) {
			constructorName = constructorName.concat(FlatInt.toString(overloadId));
		}
		instance = novaConstructors[constructorName.chars.data]();
		return instance;
	}

	static __init() {
		let self = this;

		return self;
	}

	static async execute0(_js_arguments, workingDirectory, silent, shell) {
		let self = this;

		let dataBuffer = flat_null;
		let exitCode = 0;
		workingDirectory = typeof workingDirectory === 'undefined' ? flat_null : workingDirectory;
		silent = typeof silent === 'undefined' ? false : silent;
		shell = typeof shell === 'undefined' ? true : shell;
		let stdout = FlatArray.construct();
		let stderr = FlatArray.construct();
		let commandName = _js_arguments.accessor_first();
		let processArguments = _js_arguments.skip(1).map1((_x, _i, _array) => {
				return Char.toString('"').plus0(_x.replace(FlatString.construct5("\""), FlatString.construct5("\\\""), undefined).plus0(Char.toString('"')));
		});
		exitCode = await new Promise((resolve, reject) => {
				try {
					var childProcess = System_SystemGlobal.accessor_childProcess().spawn(
						commandName.chars.data,
						processArguments.map1((_x, _i, _array) => {
								return _x.chars.data;
						}).data,
						{
							cwd: (workingDirectory) !== flat_null ? workingDirectory.chars.data : FlatString.construct5(".").chars.data,
							shell: shell
						}
					);
					childProcess.stdout.on('data', (data) => {
							dataBuffer = data.toString();
							stdout.add0(FlatString.construct4(dataBuffer));
							if (!silent) {
								process.stdout.write(dataBuffer);
							}
					});
					childProcess.stderr.on('data', (data) => {
							dataBuffer = data.toString();
							stderr.add0(FlatString.construct4(dataBuffer));
							if (!silent) {
								process.stderr.write(dataBuffer);
							}
					});
					childProcess.on('error', (err) => {
							dataBuffer = err.toString();
							reject(Exception.construct(FlatString.construct4(dataBuffer)));
					});
					childProcess.on('exit', (code) => {
							resolve(code);
					});
				} catch (e) {
					dataBuffer = e.toString();
					reject(Exception.construct(FlatString.construct4(dataBuffer)));
				}
		});
		return ExecutionResponse.construct(stdout, stderr, exitCode);
	}

	static async execute1(command, workingDirectory, silent, shell) {
		workingDirectory = typeof workingDirectory === 'undefined' ? flat_null : workingDirectory;
		silent = typeof silent === 'undefined' ? false : silent;
		shell = typeof shell === 'undefined' ? true : shell;
		return (await System.execute0(command.__callExtension(SyntaxStringFunctions.String_splitOnTopLevel2, [SyntaxStringFunctions.WHITESPACE]), workingDirectory, silent, shell));
	}

	static getEnv(name) {
		let data = flat_null;
		data = process.env[name.chars.data];
		if (typeof data !== 'string') {
			return flat_null;
		}
		return FlatString.construct4(data);
	}

	static generated152(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		return FlatArray.construct1(temp, 12);
	}

	static generated168(value0, value1, value2, value3, value4, value5, value6, value7, value8) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		return FlatArray.construct1(temp, 9);
	}

	static accessor_workingDirectory() {
		let data = flat_null;
		data = process.cwd();
		return FlatString.construct4(data);
	}

	static accessor_homeDirectory() {
		let data = flat_null;
		data = System_SystemGlobal.accessor_os().homedir();
		return FlatString.construct4(data);
	}

	static accessor__js_class() {
		return typeof System.__lazy_accessor__js_class !== 'undefined' ? System.__lazy_accessor__js_class : (System.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/system/System"), false, FlatObject.accessor__js_class(), FlatArray.construct(), System.generated152(Field.construct(FlatString.construct5("TARGET")), Field.construct(FlatString.construct5("OS")), Field.construct(FlatString.construct5("WINDOWS")), Field.construct(FlatString.construct5("LINUX")), Field.construct(FlatString.construct5("MAC_OSX")), Field.construct(FlatString.construct5("ANDROID")), Field.construct(FlatString.construct5("IOS")), Field.construct(FlatString.construct5("UNKNOWN")), Field.construct(FlatString.construct5("OS_INT")), Field.construct(FlatString.construct5("overheadTimer")), Field.construct(FlatString.construct5("workingDirectory")), Field.construct(FlatString.construct5("homeDirectory"))), System.generated168(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("exit")), Function.construct(FlatString.construct5("exit")), Function.construct(FlatString.construct5("runMain")), Function.construct(FlatString.construct5("jsCreateInstance")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("execute")), Function.construct(FlatString.construct5("execute")), Function.construct(FlatString.construct5("getEnv"))), this);
			})());
	}

	static __assignments() {
	}
	static System_SystemGlobal
}

class System_SystemGlobal extends FlatObject {
	static _childProcess = flat_null;
	static _os = flat_null;

	static generated186(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor_childProcess() {
		if (!System.System_SystemGlobal._childProcess) {
			System.System_SystemGlobal._childProcess = require('child_process');
		}
		return System.System_SystemGlobal._childProcess;
	}

	static accessor_os() {
		if (!System.System_SystemGlobal._os) {
			System.System_SystemGlobal._os = require('os');
		}
		return System.System_SystemGlobal._os;
	}

	static accessor__js_class() {
		return typeof System_SystemGlobal.__lazy_accessor__js_class !== 'undefined' ? System_SystemGlobal.__lazy_accessor__js_class : (System_SystemGlobal.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/system/System.SystemGlobal"), false, FlatObject.accessor__js_class(), FlatArray.construct(), System_SystemGlobal.generated186(Field.construct(FlatString.construct5("childProcess")), Field.construct(FlatString.construct5("os"))), FlatArray.construct(), this);
			})());
	}

	static __assignments() {
	}

	static __assignments() {
	}
}

class TakeIterator extends StreamIterator {
	position = 0;
	count = 0;

	static construct(iterator, count) {
		let __value = new TakeIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StreamIterator.__assignments.apply(__value, [].slice.call(arguments));
		TakeIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = TakeIterator.__init.call(__value, iterator, count);

		return __value;
	}

	destroy() {
	}

	static __init(iterator, count) {
		let self = this;

		self = StreamIterator.__init.call(this, iterator);
		this.count = count;
		return self;
	}

	static generated171(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated177(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof TakeIterator.__lazy_accessor__js_class !== 'undefined' ? TakeIterator.__lazy_accessor__js_class : (TakeIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/stream/TakeIterator"), false, StreamIterator.accessor__js_class(), FlatArray.construct(), TakeIterator.generated171(Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("current"))), TakeIterator.generated177(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor_hasNext() {
		return this.position++ < this.count && this.iterator.accessor_hasNext();
	}

	accessor_stepNext() {
		return this.iterator.accessor_stepNext();
	}

	accessor_next() {
		return this.iterator.accessor_next();
	}

	accessor_current() {
		return this.iterator.accessor_current();
	}

	static __assignments() {
		this.position = 0;
	}
}

class Test extends FlatObject {
	data = flat_null;
	static out = flat_null;

	static construct(data) {
		let __value = new Test();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Test.__assignments.apply(__value, [].slice.call(arguments));
		__value = Test.__init.call(__value, data);

		return __value;
	}

	destroy() {
	}

	static __init(data) {
		let self = this;

		this.data = data;
		return self;
	}

	toBe(other, customMessage) {
		let flat_local_0 = flat_null;
		customMessage = typeof customMessage === 'undefined' ? flat_null : customMessage;
		if (other !== this.data && (other === flat_null || !other.equals(this.data))) {
			let message = (flat_local_0 = customMessage) !== flat_null ? flat_local_0 : FlatString.construct5("\nexpected:\n\n    ").plus0((this.data).toString().plus0(FlatString.construct5("\n\nto be:\n\n    ").plus0((other).toString().plus0(FlatString.construct5("\n")))));
			throw InvalidAssertionException.construct(message);
			return false;
		}
		return true;
	}

	toNotBe(other, customMessage) {
		let flat_local_0 = flat_null;
		customMessage = typeof customMessage === 'undefined' ? flat_null : customMessage;
		if (other.equals(this.data) || other !== flat_null && other.equals(this.data)) {
			let message = (flat_local_0 = customMessage) !== flat_null ? flat_local_0 : FlatString.construct5("\nexpected:\n\n    ").plus0((this.data).toString().plus0(FlatString.construct5("\n\nto not be:\n\n    ").plus0((other).toString().plus0(FlatString.construct5("\n")))));
			throw InvalidAssertionException.construct(message);
			return false;
		}
		return true;
	}

	toBeTrue(func, errorMessage) {
		if (!func(this.data)) {
			throw InvalidAssertionException.construct(errorMessage(this.data));
			return false;
		}
		return true;
	}

	toThrow(exceptionType, customMessage, soft) {
		let flat_local_0 = flat_null;
		customMessage = typeof customMessage === 'undefined' ? flat_null : customMessage;
		soft = typeof soft === 'undefined' ? false : soft;
		let func = this.data;
		if (!((Exception.catchType(func, exceptionType, soft, undefined)) !== flat_null)) {
			let message = (flat_local_0 = customMessage) !== flat_null ? flat_local_0 : FlatString.construct5("Did not throw exception type ").plus0((exceptionType).toString().plus0(FlatString.construct5("")));
			throw InvalidAssertionException.construct(message);
		}
	}

	toNotThrow(exceptionType, customMessage, soft) {
		let flat_local_0 = flat_null;
		customMessage = typeof customMessage === 'undefined' ? flat_null : customMessage;
		soft = typeof soft === 'undefined' ? false : soft;
		let func = this.data;
		if ((Exception.catchType(func, exceptionType, soft, undefined)) !== flat_null) {
			let message = (flat_local_0 = customMessage) !== flat_null ? flat_local_0 : FlatString.construct5("Threw exception type ").plus0((exceptionType).toString().plus0(FlatString.construct5("")));
			throw InvalidAssertionException.construct(message);
		}
	}

	toThrowWithMessage(exceptionType, expectedMessage, customMessage, soft) {
		let flat_local_0 = flat_null;
		let flat_local_1 = flat_null;
		customMessage = typeof customMessage === 'undefined' ? flat_null : customMessage;
		soft = typeof soft === 'undefined' ? false : soft;
		let func = this.data;
		let e = Exception.catchType(func, exceptionType, soft, undefined);
		if (!((e) !== flat_null)) {
			let message = (flat_local_0 = customMessage) !== flat_null ? flat_local_0 : FlatString.construct5("Did not throw exception type ").plus0((exceptionType).toString().plus0(FlatString.construct5("")));
			throw InvalidAssertionException.construct(message);
		} else if (!e.message.equals(expectedMessage)) {
			let message = (flat_local_1 = customMessage) !== flat_null ? flat_local_1 : FlatString.construct5("Exception message of '").plus0((e.message).plus0(FlatString.construct5("' did not match '").plus0((expectedMessage).plus0(FlatString.construct5("'")))));
			throw InvalidAssertionException.construct(message);
		}
	}

	toNotThrowWithMessage(exceptionType, expectedMessage, customMessage, soft) {
		let flat_local_0 = flat_null;
		customMessage = typeof customMessage === 'undefined' ? flat_null : customMessage;
		soft = typeof soft === 'undefined' ? false : soft;
		let func = this.data;
		let e = Exception.catchType(func, exceptionType, soft, undefined);
		if ((e) !== flat_null && e.message.equals(expectedMessage)) {
			let message = (flat_local_0 = customMessage) !== flat_null ? flat_local_0 : FlatString.construct5("Threw exception type ").plus0((exceptionType).toString().plus0(FlatString.construct5("")));
			throw InvalidAssertionException.construct(message);
		}
	}

	static fail(message) {
		message = typeof message === 'undefined' ? FlatString.construct5("Failure") : message;
		throw InvalidAssertionException.construct(message);
	}

	static expect0(obj) {
		return Test.construct(obj);
	}

	static expect1(func) {
		return Test.construct(func);
	}

	static expectAsync(func) {
		return TestAsync.construct(func);
	}

	static test(description, testCode) {
		testCode();
	}

	static generated173(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated188(value0, value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		temp[6] = value6;
		temp[7] = value7;
		temp[8] = value8;
		temp[9] = value9;
		temp[10] = value10;
		temp[11] = value11;
		temp[12] = value12;
		temp[13] = value13;
		return FlatArray.construct1(temp, 14);
	}

	static accessor__js_class() {
		return typeof Test.__lazy_accessor__js_class !== 'undefined' ? Test.__lazy_accessor__js_class : (Test.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/test/Test"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Test.generated173(Field.construct(FlatString.construct5("out"))), Test.generated188(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("toBe")), Function.construct(FlatString.construct5("toNotBe")), Function.construct(FlatString.construct5("toBeTrue")), Function.construct(FlatString.construct5("toThrow")), Function.construct(FlatString.construct5("toNotThrow")), Function.construct(FlatString.construct5("toThrowWithMessage")), Function.construct(FlatString.construct5("toNotThrowWithMessage")), Function.construct(FlatString.construct5("fail")), Function.construct(FlatString.construct5("expect")), Function.construct(FlatString.construct5("expect")), Function.construct(FlatString.construct5("expectAsync")), Function.construct(FlatString.construct5("test"))), this);
			})());
	}

	static __assignments() {
	}
}

class TestAsync extends FlatObject {
	data = flat_null;
	static out = flat_null;

	static construct(data) {
		let __value = new TestAsync();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		TestAsync.__assignments.apply(__value, [].slice.call(arguments));
		__value = TestAsync.__init.call(__value, data);

		return __value;
	}

	destroy() {
	}

	static __init(data) {
		let self = this;

		this.data = data;
		return self;
	}

	async toThrow(exceptionType, customMessage, soft) {
		let flat_local_0 = flat_null;
		customMessage = typeof customMessage === 'undefined' ? flat_null : customMessage;
		soft = typeof soft === 'undefined' ? false : soft;
		let func = this.data;
		if (!(((await Exception.catchTypeAsync(func, exceptionType, soft, undefined))) !== flat_null)) {
			let message = (flat_local_0 = customMessage) !== flat_null ? flat_local_0 : FlatString.construct5("Did not throw exception type ").plus0((exceptionType).toString().plus0(FlatString.construct5("")));
			throw InvalidAssertionException.construct(message);
		}
	}

	async toNotThrow(exceptionType, customMessage, soft) {
		let flat_local_0 = flat_null;
		customMessage = typeof customMessage === 'undefined' ? flat_null : customMessage;
		soft = typeof soft === 'undefined' ? false : soft;
		let func = this.data;
		if (((await Exception.catchTypeAsync(func, exceptionType, soft, undefined))) !== flat_null) {
			let message = (flat_local_0 = customMessage) !== flat_null ? flat_local_0 : FlatString.construct5("Threw exception type ").plus0((exceptionType).toString().plus0(FlatString.construct5("")));
			throw InvalidAssertionException.construct(message);
		}
	}

	async toThrowWithMessage(exceptionType, customMessage, soft) {
		let flat_local_0 = flat_null;
		customMessage = typeof customMessage === 'undefined' ? flat_null : customMessage;
		soft = typeof soft === 'undefined' ? false : soft;
		let func = this.data;
		let e = (await Exception.catchTypeAsync(func, exceptionType, soft, undefined));
		if (!((e) !== flat_null)) {
			let message = (flat_local_0 = customMessage) !== flat_null ? flat_local_0 : FlatString.construct5("Did not throw exception type ").plus0((exceptionType).toString().plus0(FlatString.construct5("")));
			throw InvalidAssertionException.construct(message);
		}
		return Test.construct(e.message);
	}

	async toNotThrowWithMessage(exceptionType, expectedMessage, customMessage, soft) {
		let flat_local_0 = flat_null;
		customMessage = typeof customMessage === 'undefined' ? flat_null : customMessage;
		soft = typeof soft === 'undefined' ? false : soft;
		let func = this.data;
		let e = (await Exception.catchTypeAsync(func, exceptionType, soft, undefined));
		if ((e) !== flat_null && e.message.equals(expectedMessage)) {
			let message = (flat_local_0 = customMessage) !== flat_null ? flat_local_0 : FlatString.construct5("Threw exception type ").plus0((exceptionType).toString().plus0(FlatString.construct5("")));
			throw InvalidAssertionException.construct(message);
		}
	}

	static generated172(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated181(value0, value1, value2, value3, value4, value5) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		return FlatArray.construct1(temp, 6);
	}

	static accessor__js_class() {
		return typeof TestAsync.__lazy_accessor__js_class !== 'undefined' ? TestAsync.__lazy_accessor__js_class : (TestAsync.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/test/TestAsync"), false, FlatObject.accessor__js_class(), FlatArray.construct(), TestAsync.generated172(Field.construct(FlatString.construct5("out"))), TestAsync.generated181(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("toThrow")), Function.construct(FlatString.construct5("toNotThrow")), Function.construct(FlatString.construct5("toThrowWithMessage")), Function.construct(FlatString.construct5("toNotThrowWithMessage"))), this);
			})());
	}

	static __assignments() {
	}
}

class TestCase extends FlatObject {
	id = 0;
	className = flat_null;
	functionName = flat_null;
	description = flat_null;
	static staticId = 0;

	static construct(className, functionName, description) {
		let __value = new TestCase();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		TestCase.__assignments.apply(__value, [].slice.call(arguments));
		__value = TestCase.__init.call(__value, className, functionName, description);

		return __value;
	}

	destroy() {
	}

	static __init(className, functionName, description) {
		let self = this;

		description = typeof description === 'undefined' ? flat_null : description;
		this.className = className;
		this.functionName = functionName;
		this.description = description;
		return self;
	}

	equals(other) {
		return this.id === other.id;
	}

	toString() {
		return (this.className).plus0(FlatString.construct5(": \"").plus0((this.accessor_header()).plus0(FlatString.construct5("\""))));
	}

	static generated178(value0, value1, value2, value3, value4, value5) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		temp[5] = value5;
		return FlatArray.construct1(temp, 6);
	}

	static generated190(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static accessor__js_class() {
		return typeof TestCase.__lazy_accessor__js_class !== 'undefined' ? TestCase.__lazy_accessor__js_class : (TestCase.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/test/TestCase"), false, FlatObject.accessor__js_class(), FlatArray.construct(), TestCase.generated178(Field.construct(FlatString.construct5("header")), Field.construct(FlatString.construct5("id")), Field.construct(FlatString.construct5("hashCodeLong")), Field.construct(FlatString.construct5("className")), Field.construct(FlatString.construct5("functionName")), Field.construct(FlatString.construct5("description"))), TestCase.generated190(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("equals")), Function.construct(FlatString.construct5("toString"))), this);
			})());
	}

	accessor_header() {
		let flat_local_0 = flat_null;
		return (flat_local_0 = this.description) !== flat_null ? flat_local_0 : this.functionName;
	}

	accessor_hashCodeLong() {
		return this.id;
	}

	static __assignments() {
		this.id = ++TestCase.staticId;
	}
}

class TestResult extends FlatObject {
	success = false;
	timer = flat_null;
	testCase = flat_null;
	message = flat_null;

	static construct(success, timer, testCase, message) {
		let __value = new TestResult();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		TestResult.__assignments.apply(__value, [].slice.call(arguments));
		__value = TestResult.__init.call(__value, success, timer, testCase, message);

		return __value;
	}

	destroy() {
	}

	static __init(success, timer, testCase, message) {
		let self = this;

		message = typeof message === 'undefined' ? flat_null : message;
		this.success = success;
		this.timer = timer;
		this.testCase = testCase;
		this.message = message;
		return self;
	}

	toString() {
		return (this.testCase).toString().plus0(FlatString.construct5(" => ")).plus0((this.success ? FlatString.construct5("Success") : FlatString.construct5("Failure: ").plus0((this.message).plus0(FlatString.construct5("")))));
	}

	static generated185(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated193(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static accessor__js_class() {
		return typeof TestResult.__lazy_accessor__js_class !== 'undefined' ? TestResult.__lazy_accessor__js_class : (TestResult.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/test/TestResult"), false, FlatObject.accessor__js_class(), FlatArray.construct(), TestResult.generated185(Field.construct(FlatString.construct5("success")), Field.construct(FlatString.construct5("timer")), Field.construct(FlatString.construct5("testCase")), Field.construct(FlatString.construct5("message"))), TestResult.generated193(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("toString"))), this);
			})());
	}

	static __assignments() {
	}
}

class TestRunnerModel extends FlatObject {
	testCases = flat_null;
	description = flat_null;

	static construct(testCases, description) {
		let __value = new TestRunnerModel();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		TestRunnerModel.__assignments.apply(__value, [].slice.call(arguments));
		__value = TestRunnerModel.__init.call(__value, testCases, description);

		return __value;
	}

	destroy() {
	}

	static __init(testCases, description) {
		let self = this;

		description = typeof description === 'undefined' ? flat_null : description;
		this.mutator_testCases(testCases);
		this.description = description;
		return self;
	}

	toString() {
		return FlatString.construct5("TestRunnerModel").plus0(((this.description) !== flat_null ? FlatString.construct5(" \"").plus0(this.description.plus0(Char.toString('"'))) : FlatString.construct5("")));
	}

	static generated197(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static generated201(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static accessor__js_class() {
		return typeof TestRunnerModel.__lazy_accessor__js_class !== 'undefined' ? TestRunnerModel.__lazy_accessor__js_class : (TestRunnerModel.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/test/TestRunnerModel"), false, FlatObject.accessor__js_class(), FlatArray.construct(), TestRunnerModel.generated197(Field.construct(FlatString.construct5("testCases")), Field.construct(FlatString.construct5("description"))), TestRunnerModel.generated201(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("toString"))), this);
			})());
	}

	accessor_testCases() {
		return this.testCases;
	}

	mutator_testCases(value) {
		this.testCases = value;
		return value;
	}

	static __assignments() {
	}
}

class TestSuite extends FlatObject {
	testRunners = flat_null;

	static construct(testRunners) {
		let __value = new TestSuite();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		TestSuite.__assignments.apply(__value, [].slice.call(arguments));
		__value = TestSuite.__init.call(__value, testRunners);

		return __value;
	}

	destroy() {
	}

	static __init(testRunners) {
		let self = this;

		this.testRunners = testRunners;
		return self;
	}

	toString() {
		return FlatString.construct5("TestSuite").plus0(((this.testRunners) !== flat_null ? FlatString.construct5(" ").plus0(this.testRunners.toString()) : FlatString.construct5("")));
	}

	static generated195(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated198(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static accessor__js_class() {
		return typeof TestSuite.__lazy_accessor__js_class !== 'undefined' ? TestSuite.__lazy_accessor__js_class : (TestSuite.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/test/TestSuite"), false, FlatObject.accessor__js_class(), FlatArray.construct(), TestSuite.generated195(Field.construct(FlatString.construct5("testRunners"))), TestSuite.generated198(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("toString"))), this);
			})());
	}

	static __assignments() {
	}
}

class TestSuiteRunner {
	static generated196(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static accessor__js_class() {
		return typeof TestSuiteRunner.__lazy_accessor__js_class !== 'undefined' ? TestSuiteRunner.__lazy_accessor__js_class : (TestSuiteRunner.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/test/TestSuiteRunner"), true, FlatObject.accessor__js_class(), TestSuiteRunner.generated196(TestRunner.accessor__js_class()), FlatArray.construct(), FlatArray.construct(), this);
			})());
	}
}

class TestSuiteRunnerModel extends TestRunnerModel {
	testSuites = flat_null;

	static construct(testSuites) {
		let __value = new TestSuiteRunnerModel();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		TestRunnerModel.__assignments.apply(__value, [].slice.call(arguments));
		TestSuiteRunnerModel.__assignments.apply(__value, [].slice.call(arguments));
		__value = TestSuiteRunnerModel.__init.call(__value, testSuites);

		return __value;
	}

	destroy() {
	}

	static __init(testSuites) {
		let self = this;

		this.testSuites = testSuites;
		self = TestRunnerModel.__init.call(this, flat_null, flat_null);
		return self;
	}

	static generated207(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static generated213(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof TestSuiteRunnerModel.__lazy_accessor__js_class !== 'undefined' ? TestSuiteRunnerModel.__lazy_accessor__js_class : (TestSuiteRunnerModel.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/test/TestSuiteRunnerModel"), false, TestRunnerModel.accessor__js_class(), FlatArray.construct(), TestSuiteRunnerModel.generated207(Field.construct(FlatString.construct5("testCases")), Field.construct(FlatString.construct5("testRunners")), Field.construct(FlatString.construct5("testSuites"))), TestSuiteRunnerModel.generated213(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor_testCases() {
		let self = this;

		return this.accessor_testRunners().reduce((_acc, _x, _i, _list) => {
				return _acc.plus0(_x.accessor_testCases());
			}, FlatArray.construct());
	}

	accessor_testRunners() {
		let self = this;

		return this.testSuites.reduce((_acc, _x, _i, _list) => {
				return _acc.plus0(_x.testRunners);
			}, FlatArray.construct());
	}

	static __assignments() {
	}
}

class Thread extends FlatObject {
	static id = 0;

	static construct() {
		let __value = new Thread();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Thread.__assignments.apply(__value, [].slice.call(arguments));
		__value = Thread.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	start() {
		throw Exception.construct(FlatString.construct5("Not implemented for target language or runtime"));
	}

	join() {
		throw Exception.construct(FlatString.construct5("Not implemented for target language or runtime"));
	}

	static __init() {
		let self = this;

		return self;
	}

	static async sleep(millis) {
		await new Promise(resolve => {
				setTimeout(() => {
						resolve();
				}, millis);
		});
	}

	static generated206(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated215(value0, value1, value2, value3, value4) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		return FlatArray.construct1(temp, 5);
	}

	static accessor__js_class() {
		return typeof Thread.__lazy_accessor__js_class !== 'undefined' ? Thread.__lazy_accessor__js_class : (Thread.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/thread/Thread"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Thread.generated206(Field.construct(FlatString.construct5("id"))), Thread.generated215(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("start")), Function.construct(FlatString.construct5("join")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("sleep"))), this);
			})());
	}

	static __assignments() {
	}
}

class ThreadLocal extends FlatObject {
	map = flat_null;

	static construct(value) {
		let __value = new ThreadLocal();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		ThreadLocal.__assignments.apply(__value, [].slice.call(arguments));
		__value = ThreadLocal.__init.call(__value, value);

		return __value;
	}

	destroy() {
	}

	static __init(value) {
		let self = this;

		value = typeof value === 'undefined' ? flat_null : value;
		this.set(value);
		return self;
	}

	get() {
		return this.map.get(FlatLong.construct(Thread.id));
	}

	set(value) {
		return this.map.set(FlatLong.construct(Thread.id), value);
	}

	remove() {
		return this.map.remove(FlatLong.construct(Thread.id));
	}

	static generated210(value0, value1, value2, value3, value4) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		temp[4] = value4;
		return FlatArray.construct1(temp, 5);
	}

	static accessor__js_class() {
		return typeof ThreadLocal.__lazy_accessor__js_class !== 'undefined' ? ThreadLocal.__lazy_accessor__js_class : (ThreadLocal.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/thread/ThreadLocal"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), ThreadLocal.generated210(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("get")), Function.construct(FlatString.construct5("set")), Function.construct(FlatString.construct5("remove"))), this);
			})());
	}

	static __assignments() {
		this.map = HashMap.construct0(undefined, undefined);
	}
}

class Time extends FlatObject {
	static construct() {
		let __value = new Time();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Time.__assignments.apply(__value, [].slice.call(arguments));
		__value = Time.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated209(value0) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		return FlatArray.construct1(temp, 1);
	}

	static generated212(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor_currentTimeMillis() {
		return new Date().getTime();
	}

	static accessor__js_class() {
		return typeof Time.__lazy_accessor__js_class !== 'undefined' ? Time.__lazy_accessor__js_class : (Time.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/time/Time"), false, FlatObject.accessor__js_class(), FlatArray.construct(), Time.generated209(Field.construct(FlatString.construct5("currentTimeMillis"))), Time.generated212(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class UncaughtExceptionHandler extends FlatObject {
	static construct() {
		let __value = new UncaughtExceptionHandler();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		UncaughtExceptionHandler.__assignments.apply(__value, [].slice.call(arguments));
		__value = UncaughtExceptionHandler.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static __init() {
		let self = this;

		return self;
	}

	uncaughtException(thread, exception) {
	}

	static generated222(value0, value1, value2) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		return FlatArray.construct1(temp, 3);
	}

	static accessor__js_class() {
		return typeof UncaughtExceptionHandler.__lazy_accessor__js_class !== 'undefined' ? UncaughtExceptionHandler.__lazy_accessor__js_class : (UncaughtExceptionHandler.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/thread/UncaughtExceptionHandler"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), UncaughtExceptionHandler.generated222(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this")), Function.construct(FlatString.construct5("uncaughtException"))), this);
			})());
	}

	static __assignments() {
	}
}

class UnimplementedOperationException extends Exception {
	static construct(message) {
		let __value = new UnimplementedOperationException();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		Exception.__assignments.apply(__value, [].slice.call(arguments));
		UnimplementedOperationException.__assignments.apply(__value, [].slice.call(arguments));
		__value = UnimplementedOperationException.__init.call(__value, message);

		return __value;
	}

	destroy() {
	}

	static __init(message) {
		let self = this;

		self = Exception.__init.call(this, message);
		return self;
	}

	static generated221(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	static accessor__js_class() {
		return typeof UnimplementedOperationException.__lazy_accessor__js_class !== 'undefined' ? UnimplementedOperationException.__lazy_accessor__js_class : (UnimplementedOperationException.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/exception/UnimplementedOperationException"), false, Exception.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), UnimplementedOperationException.generated221(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

class UniqueIterator extends StreamIterator {
	values = flat_null;

	static construct(iterator) {
		let __value = new UniqueIterator();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		StreamIterator.__assignments.apply(__value, [].slice.call(arguments));
		UniqueIterator.__assignments.apply(__value, [].slice.call(arguments));
		__value = UniqueIterator.__init.call(__value, iterator);

		return __value;
	}

	destroy() {
	}

	static __init(iterator) {
		let self = this;

		self = StreamIterator.__init.call(this, iterator);
		return self;
	}

	static generated227(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static generated235(value0, value1) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		return FlatArray.construct1(temp, 2);
	}

	accessor_hasNext() {
		if (this.iterator.accessor_hasNext()) {
			let value = this.iterator.accessor_next();
			while (!this.values.addIfNotExists(value)) {
				this.iterator.accessor_stepNext();
				if (!this.iterator.accessor_hasNext()) {
					return false;
				}
				value = this.iterator.accessor_next();
			}
			return true;
		}
		return false;
	}

	static accessor__js_class() {
		return typeof UniqueIterator.__lazy_accessor__js_class !== 'undefined' ? UniqueIterator.__lazy_accessor__js_class : (UniqueIterator.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/stream/UniqueIterator"), false, StreamIterator.accessor__js_class(), FlatArray.construct(), UniqueIterator.generated227(Field.construct(FlatString.construct5("hasNext")), Field.construct(FlatString.construct5("stepNext")), Field.construct(FlatString.construct5("next")), Field.construct(FlatString.construct5("current"))), UniqueIterator.generated235(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	accessor_stepNext() {
		return this.iterator.accessor_stepNext();
	}

	accessor_next() {
		return this.iterator.accessor_next();
	}

	accessor_current() {
		return this.iterator.accessor_current();
	}

	static __assignments() {
		this.values = HashSet.construct0(undefined, undefined);
	}
}

class WildcardHelper extends FlatObject {
	static construct() {
		let __value = new WildcardHelper();
		FlatObject.__assignments.apply(__value, [].slice.call(arguments));
		WildcardHelper.__assignments.apply(__value, [].slice.call(arguments));
		__value = WildcardHelper.__init.call(__value);

		return __value;
	}

	destroy() {
	}

	static isWildcardMatch(value, wildcard) {
		let valueIndex = 0;
		let wildcardIndex = 0;
		let i = wildcard.indexOf0('*', undefined, undefined, undefined);
		while (i !== -1) {
			while (wildcardIndex < i) {
				if (valueIndex >= value.count) {
					return false;
				}
				if (value.get(valueIndex++) !== wildcard.get(wildcardIndex++)) {
					return false;
				}
			}
			wildcardIndex = i + 1;
			while (++i < wildcard.count && wildcard.get(i) === '*') {
				wildcardIndex++;
			}
			i = wildcard.indexOf0('*', i, undefined, undefined);
			if (i === -1 && wildcardIndex === wildcard.count) {
				return true;
			}
			valueIndex = WildcardHelper.findInnerWildcardMatch(value, wildcard, valueIndex, wildcardIndex, i === -1 ? wildcard.count : i);
			if (i === -1) {
				return valueIndex !== -1;
			}
			if (valueIndex === -1) {
				return false;
			}
		}
		return true;
	}

	static findInnerWildcardMatch(value, wildcard, valueStartIndex, wildcardStartIndex, stopIndex) {
		let wildcardIndex = wildcardStartIndex;
		let currentStartIndex = valueStartIndex;
		let currentIndex = currentStartIndex;
		while (currentIndex < value.count) {
			if (value.get(currentIndex++) !== wildcard.get(wildcardIndex++)) {
				currentStartIndex = currentIndex;
				wildcardIndex = wildcardStartIndex;
				continue;
			}
			if (currentStartIndex === valueStartIndex) {
				continue;
			}
			if (wildcardIndex === stopIndex) {
				return currentStartIndex;
			}
		}
		return -1;
	}

	static __init() {
		let self = this;

		return self;
	}

	static generated226(value0, value1, value2, value3) {
		let temp = flat_null;
		temp = [];
		temp[0] = value0;
		temp[1] = value1;
		temp[2] = value2;
		temp[3] = value3;
		return FlatArray.construct1(temp, 4);
	}

	static accessor__js_class() {
		return typeof WildcardHelper.__lazy_accessor__js_class !== 'undefined' ? WildcardHelper.__lazy_accessor__js_class : (WildcardHelper.__lazy_accessor__js_class = (() => {
					return Class.construct1(FlatString.construct5("flat/log/WildcardHelper"), false, FlatObject.accessor__js_class(), FlatArray.construct(), FlatArray.construct(), WildcardHelper.generated226(Function.construct(FlatString.construct5("construct")), Function.construct(FlatString.construct5("isWildcardMatch")), Function.construct(FlatString.construct5("findInnerWildcardMatch")), Function.construct(FlatString.construct5("this"))), this);
			})());
	}

	static __assignments() {
	}
}

__copyStaticFunctions(FlatObject, App, PlusEqualsOperator, PlusOperator, OrderedList, List, FlatArray, Iterator, ArrayIterator, Async, Primitive, Comparable, Bool, MultiplyEqualsOperator, MultiplyOperator, MinusEqualsOperator, MinusOperator, Number, Integer, FlatByte, Exception, CancellationException, CaughtException, Char, Class, ClosedStreamException, Colorizer, FlatConsole, OutputStream, ConsoleOutputStream, Timer, CumulativeTimer, Curl, FlatDate, GreaterThanOrEqualToOperator, GreaterThanOperator, LessThanOrEqualToOperator, LessThanOperator, FlatDateTime, DateTime_InvalidDateException, DateTime_InvalidDateFormatException, DateTime_Calculator, DivideByZeroException, RealNumber, FlatDouble, EmptyOutputStream, NoSuchElementException, EmptyStackException, EscapeCode, EscapeCodeIterator, EventStream, ExceptionData, ExecutionResponse, FancyOutputStream, Field, flat_meta_Field_Builder, File, File_FileGlobal, FileNotFoundException, InputStream, FileReader, FileWriter, StreamIterator, FilterIterator, FilterNotIterator, FlatMapIterator, FlatFloat, Function, flat_meta_Function_Builder, Future, Future_CompletableFuture, HashMap, HashMap_HashMapIterator, HashSet, HashSet_HashSetIterator, Import, TestRunner, Import_Test, Import_TestSuite, FlatInt, IntRange, IntRangeIterator, InvalidArgumentException, TestException, InvalidAssertionException, InvalidOperationException, Iterable, LinkedList, LinkedListIterator, ListNode, Logger, FlatLong, MapIterator, Match, MatchGroup, flat_regex_MatchGroup_Builder, FlatMath, NotEqualToOperator, FlatString, Null, NullAccessException, Pair, Pattern, PeekIterator, Queue, Regex, RegexStringExtensions, FlatShort, SkipIterator, Stack, StackTrace, Stream, StreamListExtensions, StreamReader, StringBuilder, StringCharArray, SyntaxStringFunctions, System, System_SystemGlobal, TakeIterator, Test, TestAsync, TestCase, TestResult, TestRunnerModel, TestSuite, TestSuiteRunner, TestSuiteRunnerModel, Thread, ThreadLocal, Time, UncaughtExceptionHandler, UnimplementedOperationException, UniqueIterator, WildcardHelper);

__copyPrototype
  (OrderedList, List, Iterable)
  (List, Iterable)
  (FlatArray, List, OrderedList, PlusOperator, PlusEqualsOperator, Iterable)
  (ArrayIterator, Iterator)
  (Bool, Comparable)
  (Number, PlusOperator, PlusEqualsOperator, MinusOperator, MinusEqualsOperator, MultiplyOperator, MultiplyEqualsOperator, Comparable)
  (FlatByte, Integer, PlusOperator, PlusEqualsOperator, MinusOperator, MinusEqualsOperator, MultiplyOperator, MultiplyEqualsOperator, Comparable)
  (Char, Integer, PlusOperator, PlusEqualsOperator, MinusOperator, MinusEqualsOperator, MultiplyOperator, MultiplyEqualsOperator, Comparable)
  (ConsoleOutputStream, OutputStream)
  (FlatDateTime, Comparable, LessThanOperator, LessThanOrEqualToOperator, GreaterThanOperator, GreaterThanOrEqualToOperator)
  (FlatDouble, RealNumber, PlusOperator, PlusEqualsOperator, MinusOperator, MinusEqualsOperator, MultiplyOperator, MultiplyEqualsOperator, Comparable)
  (EmptyOutputStream, OutputStream)
  (EscapeCodeIterator, Iterator)
  (FancyOutputStream, OutputStream)
  (FileReader, InputStream)
  (FileWriter, OutputStream)
  (StreamIterator, Iterator)
  (FilterIterator, Iterator)
  (FilterNotIterator, Iterator)
  (FlatMapIterator, Iterator)
  (FlatFloat, RealNumber, PlusOperator, PlusEqualsOperator, MinusOperator, MinusEqualsOperator, MultiplyOperator, MultiplyEqualsOperator, Comparable)
  (HashMap, List, Iterable)
  (HashMap_HashMapIterator, Iterator)
  (HashSet, List, Iterable)
  (HashSet_HashSetIterator, Iterator)
  (Import_Test, TestRunner)
  (Import_TestSuite, TestRunner)
  (FlatInt, Integer, PlusOperator, PlusEqualsOperator, MinusOperator, MinusEqualsOperator, MultiplyOperator, MultiplyEqualsOperator, Comparable)
  (IntRange, List, Iterable)
  (IntRangeIterator, Iterator)
  (LinkedList, List, Iterable)
  (LinkedListIterator, Iterator)
  (FlatLong, Integer, PlusOperator, PlusEqualsOperator, MinusOperator, MinusEqualsOperator, MultiplyOperator, MultiplyEqualsOperator, Comparable)
  (MapIterator, Iterator)
  (FlatString, Comparable, PlusOperator, MultiplyOperator)
  (Null, Comparable, PlusOperator, MultiplyOperator)
  (PeekIterator, Iterator)
  (Queue, List, Iterable)
  (FlatShort, Integer, PlusOperator, PlusEqualsOperator, MinusOperator, MinusEqualsOperator, MultiplyOperator, MultiplyEqualsOperator, Comparable)
  (SkipIterator, Iterator)
  (StringBuilder, OutputStream)
  (TakeIterator, Iterator)
  (TestSuiteRunner, TestRunner)
  (UniqueIterator, Iterator);

flat_null = Null.construct();

FlatObject.__lazy_accessor__js_class = undefined;
App.__lazy_accessor__js_class = undefined;
PlusEqualsOperator.__lazy_accessor__js_class = undefined;
PlusOperator.__lazy_accessor__js_class = undefined;
OrderedList.__lazy_accessor__js_class = undefined;
List.__lazy_accessor__js_class = undefined;
FlatArray.__lazy_accessor__js_class = undefined;
Iterator.__lazy_accessor__js_class = undefined;
ArrayIterator.__lazy_accessor__js_class = undefined;
Async.__lazy_accessor__js_class = undefined;
Primitive.__lazy_accessor__js_class = undefined;
Comparable.__lazy_accessor__js_class = undefined;
Bool.__lazy_accessor__js_class = undefined;
MultiplyEqualsOperator.__lazy_accessor__js_class = undefined;
MultiplyOperator.__lazy_accessor__js_class = undefined;
MinusEqualsOperator.__lazy_accessor__js_class = undefined;
MinusOperator.__lazy_accessor__js_class = undefined;
Number.__lazy_accessor__js_class = undefined;
Integer.__lazy_accessor__js_class = undefined;
FlatByte.__lazy_accessor__js_class = undefined;
Exception.__lazy_accessor__js_class = undefined;
CancellationException.__lazy_accessor__js_class = undefined;
CaughtException.__lazy_accessor__js_class = undefined;
Char.__lazy_accessor__js_class = undefined;
Class.__lazy_accessor__js_class = undefined;
Class.__lazy_accessor_ALL = undefined;
ClosedStreamException.__lazy_accessor__js_class = undefined;
Colorizer.__lazy_accessor__js_class = undefined;
FlatConsole.__lazy_accessor__js_class = undefined;
OutputStream.__lazy_accessor__js_class = undefined;
ConsoleOutputStream.__lazy_accessor__js_class = undefined;
Timer.__lazy_accessor__js_class = undefined;
CumulativeTimer.__lazy_accessor__js_class = undefined;
Curl.__lazy_accessor__js_class = undefined;
FlatDate.__lazy_accessor__js_class = undefined;
GreaterThanOrEqualToOperator.__lazy_accessor__js_class = undefined;
GreaterThanOperator.__lazy_accessor__js_class = undefined;
LessThanOrEqualToOperator.__lazy_accessor__js_class = undefined;
LessThanOperator.__lazy_accessor__js_class = undefined;
FlatDateTime.__lazy_accessor__js_class = undefined;
DateTime_InvalidDateException.__lazy_accessor__js_class = undefined;
DateTime_InvalidDateFormatException.__lazy_accessor__js_class = undefined;
DateTime_Calculator.__lazy_accessor__js_class = undefined;
DivideByZeroException.__lazy_accessor__js_class = undefined;
RealNumber.__lazy_accessor__js_class = undefined;
FlatDouble.__lazy_accessor__js_class = undefined;
EmptyOutputStream.__lazy_accessor__js_class = undefined;
NoSuchElementException.__lazy_accessor__js_class = undefined;
EmptyStackException.__lazy_accessor__js_class = undefined;
EscapeCode.__lazy_accessor__js_class = undefined;
EscapeCodeIterator.__lazy_accessor__js_class = undefined;
EscapeCodeIterator.__lazy_accessor_EXACT_ESCAPE_CODES = undefined;
EscapeCodeIterator.__lazy_accessor_VARIABLE_ESCAPE_CODES = undefined;
EventStream.__lazy_accessor__js_class = undefined;
ExceptionData.__lazy_accessor__js_class = undefined;
ExecutionResponse.__lazy_accessor__js_class = undefined;
FancyOutputStream.__lazy_accessor__js_class = undefined;
Field.__lazy_accessor__js_class = undefined;
flat_meta_Field_Builder.__lazy_accessor__js_class = undefined;
File.__lazy_accessor__js_class = undefined;
File_FileGlobal.__lazy_accessor_fs = undefined;
File_FileGlobal.__lazy_accessor_path = undefined;
File_FileGlobal.__lazy_accessor__js_class = undefined;
FileNotFoundException.__lazy_accessor__js_class = undefined;
InputStream.__lazy_accessor__js_class = undefined;
FileReader.__lazy_accessor__js_class = undefined;
FileWriter.__lazy_accessor__js_class = undefined;
StreamIterator.__lazy_accessor__js_class = undefined;
FilterIterator.__lazy_accessor__js_class = undefined;
FilterNotIterator.__lazy_accessor__js_class = undefined;
FlatMapIterator.__lazy_accessor__js_class = undefined;
FlatFloat.__lazy_accessor__js_class = undefined;
Function.__lazy_accessor__js_class = undefined;
flat_meta_Function_Builder.__lazy_accessor__js_class = undefined;
Future.__lazy_accessor__js_class = undefined;
Future_CompletableFuture.__lazy_accessor__js_class = undefined;
HashMap.__lazy_accessor__js_class = undefined;
HashMap_HashMapIterator.__lazy_accessor__js_class = undefined;
HashSet.__lazy_accessor__js_class = undefined;
HashSet_HashSetIterator.__lazy_accessor__js_class = undefined;
Import.__lazy_accessor__js_class = undefined;
TestRunner.__lazy_accessor__js_class = undefined;
Import_Test.__lazy_accessor__js_class = undefined;
Import_TestSuite.__lazy_accessor__js_class = undefined;
FlatInt.__lazy_accessor__js_class = undefined;
IntRange.__lazy_accessor__js_class = undefined;
IntRangeIterator.__lazy_accessor__js_class = undefined;
InvalidArgumentException.__lazy_accessor__js_class = undefined;
TestException.__lazy_accessor__js_class = undefined;
InvalidAssertionException.__lazy_accessor__js_class = undefined;
InvalidOperationException.__lazy_accessor__js_class = undefined;
Iterable.__lazy_accessor__js_class = undefined;
LinkedList.__lazy_accessor__js_class = undefined;
LinkedListIterator.__lazy_accessor__js_class = undefined;
ListNode.__lazy_accessor__js_class = undefined;
Logger.__lazy_accessor__js_class = undefined;
FlatLong.__lazy_accessor__js_class = undefined;
MapIterator.__lazy_accessor__js_class = undefined;
Match.__lazy_accessor__js_class = undefined;
MatchGroup.__lazy_accessor__js_class = undefined;
flat_regex_MatchGroup_Builder.__lazy_accessor__js_class = undefined;
FlatMath.__lazy_accessor__js_class = undefined;
NotEqualToOperator.__lazy_accessor__js_class = undefined;
FlatString.__lazy_accessor__js_class = undefined;
Null.__lazy_accessor__js_class = undefined;
NullAccessException.__lazy_accessor__js_class = undefined;
Pair.__lazy_accessor__js_class = undefined;
Pattern.__lazy_accessor__js_class = undefined;
PeekIterator.__lazy_accessor__js_class = undefined;
Queue.__lazy_accessor__js_class = undefined;
Regex.__lazy_accessor__js_class = undefined;
RegexStringExtensions.__lazy_accessor__js_class = undefined;
FlatShort.__lazy_accessor__js_class = undefined;
SkipIterator.__lazy_accessor__js_class = undefined;
Stack.__lazy_accessor__js_class = undefined;
StackTrace.__lazy_accessor__js_class = undefined;
Stream.__lazy_accessor__js_class = undefined;
StreamListExtensions.__lazy_accessor__js_class = undefined;
StreamReader.__lazy_accessor__js_class = undefined;
StringBuilder.__lazy_accessor__js_class = undefined;
StringCharArray.__lazy_accessor__js_class = undefined;
SyntaxStringFunctions.__lazy_accessor__js_class = undefined;
System.__lazy_accessor__js_class = undefined;
System_SystemGlobal.__lazy_accessor__js_class = undefined;
TakeIterator.__lazy_accessor__js_class = undefined;
Test.__lazy_accessor__js_class = undefined;
TestAsync.__lazy_accessor__js_class = undefined;
TestCase.__lazy_accessor__js_class = undefined;
TestResult.__lazy_accessor__js_class = undefined;
TestRunnerModel.__lazy_accessor__js_class = undefined;
TestSuite.__lazy_accessor__js_class = undefined;
TestSuiteRunner.__lazy_accessor__js_class = undefined;
TestSuiteRunnerModel.__lazy_accessor__js_class = undefined;
Thread.__lazy_accessor__js_class = undefined;
ThreadLocal.__lazy_accessor__js_class = undefined;
Time.__lazy_accessor__js_class = undefined;
UncaughtExceptionHandler.__lazy_accessor__js_class = undefined;
UnimplementedOperationException.__lazy_accessor__js_class = undefined;
UniqueIterator.__lazy_accessor__js_class = undefined;
WildcardHelper.__lazy_accessor__js_class = undefined;

FlatDateTime.DateTime_InvalidDateException = DateTime_InvalidDateException;
FlatDateTime.DateTime_InvalidDateFormatException = DateTime_InvalidDateFormatException;
FlatDateTime.DateTime_Calculator = DateTime_Calculator;
Field.flat_meta_Field_Builder = flat_meta_Field_Builder;
File.File_FileGlobal = File_FileGlobal;
Function.flat_meta_Function_Builder = flat_meta_Function_Builder;
Future.Future_CompletableFuture = Future_CompletableFuture;
HashMap.HashMap_HashMapIterator = HashMap_HashMapIterator;
HashSet.HashSet_HashSetIterator = HashSet_HashSetIterator;
MatchGroup.flat_regex_MatchGroup_Builder = flat_regex_MatchGroup_Builder;
System.System_SystemGlobal = System_SystemGlobal;

var App0_Flat_init_static_initialized = false;
function App0_Flat_init_static() {
	if (!App0_Flat_init_static_initialized) {
		App0_Flat_init_static_initialized = true;
		App.name = FlatString.construct5("NodeImport");
		App.version = FlatString.construct5("0.1.0");
		App.description = FlatString.construct5("Node module importing package");
		App.author = FlatString.construct5("Braden Steffaniak");
		App.license = FlatString.construct5("ISC");
		App.defaultTarget = FlatString.construct5("es6");
	}
}

var Colorizer0_Flat_init_static_initialized = false;
function Colorizer0_Flat_init_static() {
	if (!Colorizer0_Flat_init_static_initialized) {
		Colorizer0_Flat_init_static_initialized = true;
		Colorizer.PREFIX = FlatString.construct5("\x1b[");
		Colorizer.RESET = FlatString.construct5("0");
		Colorizer.BRIGHT = FlatString.construct5("1");
		Colorizer.DIM = FlatString.construct5("2");
		Colorizer.UNDERSCORE = FlatString.construct5("4");
		Colorizer.BLINK = FlatString.construct5("5");
		Colorizer.REVERSE = FlatString.construct5("7");
		Colorizer.HIDDEN = FlatString.construct5("8");
		Colorizer.BLACK_FOREGROUND = FlatString.construct5("30");
		Colorizer.RED_FOREGROUND = FlatString.construct5("31");
		Colorizer.GREEN_FOREGROUND = FlatString.construct5("32");
		Colorizer.YELLOW_FOREGROUND = FlatString.construct5("33");
		Colorizer.BLUE_FOREGROUND = FlatString.construct5("34");
		Colorizer.MAGENTA_FOREGROUND = FlatString.construct5("35");
		Colorizer.CYAN_FOREGROUND = FlatString.construct5("36");
		Colorizer.WHITE_FOREGROUND = FlatString.construct5("37");
		Colorizer.RGB_FOREGROUND = FlatString.construct5("38");
		Colorizer.GRAY_FOREGROUND = FlatString.construct5("90");
		Colorizer.BRIGHT_BLACK_FOREGROUND = FlatString.construct5("90");
		Colorizer.BRIGHT_RED_FOREGROUND = FlatString.construct5("91");
		Colorizer.BRIGHT_GREEN_FOREGROUND = FlatString.construct5("92");
		Colorizer.BRIGHT_YELLOW_FOREGROUND = FlatString.construct5("93");
		Colorizer.BRIGHT_BLUE_FOREGROUND = FlatString.construct5("94");
		Colorizer.BRIGHT_MAGENTA_FOREGROUND = FlatString.construct5("95");
		Colorizer.BRIGHT_CYAN_FOREGROUND = FlatString.construct5("96");
		Colorizer.BRIGHT_WHITE_FOREGROUND = FlatString.construct5("97");
		Colorizer.BLACK_BACKGROUND = FlatString.construct5("40");
		Colorizer.RED_BACKGROUND = FlatString.construct5("41");
		Colorizer.GREEN_BACKGROUND = FlatString.construct5("42");
		Colorizer.YELLOW_BACKGROUND = FlatString.construct5("43");
		Colorizer.BLUE_BACKGROUND = FlatString.construct5("44");
		Colorizer.MAGENTA_BACKGROUND = FlatString.construct5("45");
		Colorizer.CYAN_BACKGROUND = FlatString.construct5("46");
		Colorizer.WHITE_BACKGROUND = FlatString.construct5("47");
		Colorizer.RGB_BACKGROUND = FlatString.construct5("48");
		Colorizer.GRAY_BACKGROUND = FlatString.construct5("100");
		Colorizer.BRIGHT_BLACK_BACKGROUND = FlatString.construct5("100");
		Colorizer.BRIGHT_RED_BACKGROUND = FlatString.construct5("101");
		Colorizer.BRIGHT_GREEN_BACKGROUND = FlatString.construct5("102");
		Colorizer.BRIGHT_YELLOW_BACKGROUND = FlatString.construct5("103");
		Colorizer.BRIGHT_BLUE_BACKGROUND = FlatString.construct5("104");
		Colorizer.BRIGHT_MAGENTA_BACKGROUND = FlatString.construct5("105");
		Colorizer.BRIGHT_CYAN_BACKGROUND = FlatString.construct5("106");
		Colorizer.BRIGHT_WHITE_BACKGROUND = FlatString.construct5("107");
	}
}

var FlatConsole0_Flat_init_static_initialized = false;
function FlatConsole0_Flat_init_static() {
	if (!FlatConsole0_Flat_init_static_initialized) {
		FlatConsole0_Flat_init_static_initialized = true;
		FlatConsole.out = ConsoleOutputStream.construct0(FlatString.construct5("log"));
		FlatConsole.warn = ConsoleOutputStream.construct0(FlatString.construct5("warn"));
		FlatConsole.error = ConsoleOutputStream.construct0(FlatString.construct5("error"));
	}
}

var OutputStream0_Flat_init_static_initialized = false;
function OutputStream0_Flat_init_static() {
	if (!OutputStream0_Flat_init_static_initialized) {
		OutputStream0_Flat_init_static_initialized = true;
		OutputStream.EMPTY = EmptyOutputStream.construct();
	}
}

var FlatDateTime0_Flat_init_static_initialized = false;
function FlatDateTime0_Flat_init_static() {
	if (!FlatDateTime0_Flat_init_static_initialized) {
		FlatDateTime0_Flat_init_static_initialized = true;
		FlatDateTime.SECS_DAY = 24 * 60 * 60 * 1000;
		FlatDateTime.SECS_HOUR = 60 * 60 * 1000;
		FlatDateTime.SECS_MINUTE = 60 * 1000;
		FlatDateTime.EPOCH_YEAR = 1970;
		FlatDateTime.MONTH_DAYS = FlatDateTime.generated264();
		FlatDateTime.LEAP_MONTH_DAYS = FlatDateTime.generated265();
	}
}

var EscapeCode0_Flat_init_static_initialized = false;
function EscapeCode0_Flat_init_static() {
	if (!EscapeCode0_Flat_init_static_initialized) {
		EscapeCode0_Flat_init_static_initialized = true;
		EscapeCode.ESCAPE = FlatString.construct5("\x1B");
		EscapeCode.MODE_SUFFIX = FlatString.construct5("m");
		EscapeCode.RESET = FlatString.construct5("[0m");
		EscapeCode.BRIGHT = FlatString.construct5("[1m");
		EscapeCode.DIM = FlatString.construct5("[2m");
		EscapeCode.UNDERSCORE = FlatString.construct5("[4m");
		EscapeCode.BLINK = FlatString.construct5("[5m");
		EscapeCode.REVERSE = FlatString.construct5("[7m");
		EscapeCode.HIDDEN = FlatString.construct5("[8m");
		EscapeCode.BLACK_FOREGROUND = FlatString.construct5("[30m");
		EscapeCode.RED_FOREGROUND = FlatString.construct5("[31m");
		EscapeCode.GREEN_FOREGROUND = FlatString.construct5("[32m");
		EscapeCode.YELLOW_FOREGROUND = FlatString.construct5("[33m");
		EscapeCode.BLUE_FOREGROUND = FlatString.construct5("[34m");
		EscapeCode.MAGENTA_FOREGROUND = FlatString.construct5("[35m");
		EscapeCode.CYAN_FOREGROUND = FlatString.construct5("[36m");
		EscapeCode.WHITE_FOREGROUND = FlatString.construct5("[37m");
		EscapeCode.RGB_FOREGROUND_PREFIX = FlatString.construct5("[38;2;");
		EscapeCode.RGB_FOREGROUND_256_PREFIX = FlatString.construct5("[38;5;");
		EscapeCode.GRAY_FOREGROUND = FlatString.construct5("[90m");
		EscapeCode.BRIGHT_BLACK_FOREGROUND = FlatString.construct5("[90m");
		EscapeCode.BRIGHT_RED_FOREGROUND = FlatString.construct5("[91m");
		EscapeCode.BRIGHT_GREEN_FOREGROUND = FlatString.construct5("[92m");
		EscapeCode.BRIGHT_YELLOW_FOREGROUND = FlatString.construct5("[93m");
		EscapeCode.BRIGHT_BLUE_FOREGROUND = FlatString.construct5("[94m");
		EscapeCode.BRIGHT_MAGENTA_FOREGROUND = FlatString.construct5("[95m");
		EscapeCode.BRIGHT_CYAN_FOREGROUND = FlatString.construct5("[96m");
		EscapeCode.BRIGHT_WHITE_FOREGROUND = FlatString.construct5("[97m");
		EscapeCode.BLACK_BACKGROUND = FlatString.construct5("[40m");
		EscapeCode.RED_BACKGROUND = FlatString.construct5("[41m");
		EscapeCode.GREEN_BACKGROUND = FlatString.construct5("[42m");
		EscapeCode.YELLOW_BACKGROUND = FlatString.construct5("[43m");
		EscapeCode.BLUE_BACKGROUND = FlatString.construct5("[44m");
		EscapeCode.MAGENTA_BACKGROUND = FlatString.construct5("[45m");
		EscapeCode.CYAN_BACKGROUND = FlatString.construct5("[46m");
		EscapeCode.WHITE_BACKGROUND = FlatString.construct5("[47m");
		EscapeCode.RGB_BACKGROUND_PREFIX = FlatString.construct5("[48;2;");
		EscapeCode.RGB_BACKGROUND_256_PREFIX = FlatString.construct5("[48;5;");
		EscapeCode.GRAY_BACKGROUND = FlatString.construct5("[100m");
		EscapeCode.BRIGHT_BLACK_BACKGROUND = FlatString.construct5("[100m");
		EscapeCode.BRIGHT_RED_BACKGROUND = FlatString.construct5("[101m");
		EscapeCode.BRIGHT_GREEN_BACKGROUND = FlatString.construct5("[102m");
		EscapeCode.BRIGHT_YELLOW_BACKGROUND = FlatString.construct5("[103m");
		EscapeCode.BRIGHT_BLUE_BACKGROUND = FlatString.construct5("[104m");
		EscapeCode.BRIGHT_MAGENTA_BACKGROUND = FlatString.construct5("[105m");
		EscapeCode.BRIGHT_CYAN_BACKGROUND = FlatString.construct5("[106m");
		EscapeCode.BRIGHT_WHITE_BACKGROUND = FlatString.construct5("[107m");
		EscapeCode.CURSOR_MOVE_TO_HOME = FlatString.construct5("[H");
		EscapeCode.CURSOR_MOVE_TO_POSITION_PREFIX = FlatString.construct5("[");
		EscapeCode.CURSOR_MOVE_TO_POSITION_SUFFIX = FlatString.construct5("H");
		EscapeCode.CURSOR_MOVE_UP_PREFIX = FlatString.construct5("[");
		EscapeCode.CURSOR_MOVE_UP_SUFFIX = FlatString.construct5("A");
		EscapeCode.CURSOR_MOVE_DOWN_PREFIX = FlatString.construct5("[");
		EscapeCode.CURSOR_MOVE_DOWN_SUFFIX = FlatString.construct5("B");
		EscapeCode.CURSOR_MOVE_RIGHT_PREFIX = FlatString.construct5("[");
		EscapeCode.CURSOR_MOVE_RIGHT_SUFFIX = FlatString.construct5("C");
		EscapeCode.CURSOR_MOVE_LEFT_PREFIX = FlatString.construct5("[");
		EscapeCode.CURSOR_MOVE_LEFT_SUFFIX = FlatString.construct5("D");
		EscapeCode.CURSOR_MOVE_DOWN_AT_BEGINNING_OF_LINE_PREFIX = FlatString.construct5("[");
		EscapeCode.CURSOR_MOVE_DOWN_AT_BEGINNING_OF_LINE_SUFFIX = FlatString.construct5("E");
		EscapeCode.CURSOR_MOVE_UP_AT_BEGINNING_OF_LINE_PREFIX = FlatString.construct5("[");
		EscapeCode.CURSOR_MOVE_UP_AT_BEGINNING_OF_LINE_SUFFIX = FlatString.construct5("F");
		EscapeCode.CURSOR_MOVE_TO_COLUMN_PREFIX = FlatString.construct5("[");
		EscapeCode.CURSOR_MOVE_TO_COLUMN_SUFFIX = FlatString.construct5("G");
		EscapeCode.CURSOR_REQUEST_POSITION = FlatString.construct5("6n");
		EscapeCode.CURSOR_MOVE_UP_ONE_LINE = FlatString.construct5(" M");
		EscapeCode.CURSOR_SAVE_POSITION = FlatString.construct5(" 7");
		EscapeCode.CURSOR_RESTORE_POSITION = FlatString.construct5(" 8");
		EscapeCode.ERASE_FROM_CURSOR_TO_END_OF_SCREEN = FlatString.construct5("[0J");
		EscapeCode.ERASE_FROM_CURSOR_TO_BEGINNING_OF_SCREEN = FlatString.construct5("[1J");
		EscapeCode.ERASE_ENTIRE_SCREEN = FlatString.construct5("[2J");
		EscapeCode.ERASE_SAVED_LINES = FlatString.construct5("[3J");
		EscapeCode.ERASE_FROM_CURSOR_TO_END_OF_LINE = FlatString.construct5("[0K");
		EscapeCode.ERASE_FROM_CURSOR_TO_BEGINNING_OF_LINE = FlatString.construct5("[1K");
		EscapeCode.ERASE_ENTIRE_LINE = FlatString.construct5("[2K");
	}
}

var EventStream0_Flat_init_static_initialized = false;
function EventStream0_Flat_init_static() {
	if (!EventStream0_Flat_init_static_initialized) {
		EventStream0_Flat_init_static_initialized = true;
		Logger0_Flat_init_static();
		Logger1_Flat_init_static();
		EventStream.log = Logger.construct(EventStream.accessor__js_class(), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined);
	}
}

var File0_Flat_init_static_initialized = false;
function File0_Flat_init_static() {
	if (!File0_Flat_init_static_initialized) {
		File0_Flat_init_static_initialized = true;
		FlatString0_Flat_init_static();
		FlatInt0_Flat_init_static();
		FlatLong0_Flat_init_static();
		File.EXECUTED_FILE = File.getExecutedFile();
		File.EXECUTED_DIRECTORY = File.getExecutedDirectory();
	}
}

var File1_Flat_init_static_initialized = false;
function File1_Flat_init_static() {
	if (!File1_Flat_init_static_initialized) {
		File1_Flat_init_static_initialized = true;
		FlatLong0_Flat_init_static();
		FlatInt0_Flat_init_static();
		FlatString0_Flat_init_static();
		let exitLog = File.construct1(FlatString.construct5("log").plus0(FlatLong.toString((Time.accessor_currentTimeMillis())).plus0(FlatString.construct5(""))));
	}
}

var Import_Test0_Flat_init_static_initialized = false;
function Import_Test0_Flat_init_static() {
	if (!Import_Test0_Flat_init_static_initialized) {
		Import_Test0_Flat_init_static_initialized = true;
		TestCase0_Flat_init_static();
		Import_Test._can_import_cryptoTestCase = TestCase.construct(FlatString.construct5("Import_Test"), FlatString.construct5("can import crypto"), undefined);
		Import_Test._can_use_cryptoTestCase = TestCase.construct(FlatString.construct5("Import_Test"), FlatString.construct5("can use crypto"), undefined);
		Import_Test._runTestsTestRunner = TestRunnerModel.construct(Import_Test.generated266(Import_Test._can_import_cryptoTestCase, Import_Test._can_use_cryptoTestCase), undefined);
	}
}

var Import_TestSuite0_Flat_init_static_initialized = false;
function Import_TestSuite0_Flat_init_static() {
	if (!Import_TestSuite0_Flat_init_static_initialized) {
		Import_TestSuite0_Flat_init_static_initialized = true;
		Import_Test0_Flat_init_static();
		Import_TestSuite._runTestsTestSuite = TestSuiteRunnerModel.construct(Import_TestSuite.generated268(TestSuite.construct(Import_TestSuite.generated267(Import_Test._runTestsTestRunner))));
	}
}

var FlatInt0_Flat_init_static_initialized = false;
function FlatInt0_Flat_init_static() {
	if (!FlatInt0_Flat_init_static_initialized) {
		FlatInt0_Flat_init_static_initialized = true;
		FlatInt.MAX_VALUE = 2147483647;
		FlatInt.MIN_VALUE = -2147483648;
	}
}

var Logger0_Flat_init_static_initialized = false;
function Logger0_Flat_init_static() {
	if (!Logger0_Flat_init_static_initialized) {
		Logger0_Flat_init_static_initialized = true;
		FlatConsole0_Flat_init_static();
		Logger.TRACE = 6;
		Logger.DEBUG = 5;
		Logger.INFO = 4;
		Logger.WARN = 3;
		Logger.ERROR = 2;
		Logger.FATAL = 1;
		Logger.OFF = 0;
		Logger.defaultShowPrefix = Bool.construct(false);
		Logger.defaultShowFormatting = Bool.construct(true);
		Logger.defaultLineLength = flat_null;
		Logger.defaultSplitOnWord = Bool.construct(true);
		Logger.defaultLinePrefix = flat_null;
		Logger.defaultLineSuffix = flat_null;
		Logger.defaultPrefix = flat_null;
		Logger.defaultSuffix = flat_null;
		Logger.defaultPrefixSeparator = FlatString.construct5(" - ");
		Logger.forceLoggingLevel = flat_null;
		Logger.forceShowPrefix = flat_null;
		Logger.forceShowFormatting = flat_null;
		Logger.forceLineLength = flat_null;
		Logger.forceSplitOnWord = flat_null;
		Logger.forceLinePrefix = flat_null;
		Logger.forceLineSuffix = flat_null;
		Logger.forcePrefix = flat_null;
		Logger.forceSuffix = flat_null;
		Logger.forcePrefixSeparator = flat_null;
		Logger.defaultTraceStreams = Logger.generated269(FlatConsole.out);
		Logger.defaultDebugStreams = Logger.generated270(FlatConsole.out);
		Logger.defaultInfoStreams = Logger.generated271(FlatConsole.out);
		Logger.defaultWarnStreams = Logger.generated272(FlatConsole.warn);
		Logger.defaultErrorStreams = Logger.generated273(FlatConsole.error);
		Logger.defaultFatalStreams = Logger.generated274(FlatConsole.error);
		Logger._defaultLoggingLevel = Logger.INFO;
		Logger.currentDisabledLoggersModificationId = 1;
		Logger.classLoggingLevels = HashMap.construct0(undefined, undefined);
		Logger.labelLoggingLevels = HashMap.construct0(undefined, undefined);
		Logger.wildcardLabelLoggingLevels = HashMap.construct0(undefined, undefined);
	}
}

var Logger1_Flat_init_static_initialized = false;
function Logger1_Flat_init_static() {
	if (!Logger1_Flat_init_static_initialized) {
		Logger1_Flat_init_static_initialized = true;
		System0_Flat_init_static();
		System1_Flat_init_static();
		FlatInt0_Flat_init_static();
		FlatMath0_Flat_init_static();
		FlatLong0_Flat_init_static();
		Stream0_Flat_init_static();
		FlatString0_Flat_init_static();
		let labelLoggingLevels = flat_null;
		let traceLoggingLabels = flat_null;
		let debugLoggingLabels = flat_null;
		let infoLoggingLabels = flat_null;
		let warnLoggingLabels = flat_null;
		let errorLoggingLabels = flat_null;
		let fatalLoggingLabels = flat_null;
		let offLoggingLabels = flat_null;
		let level = flat_null;
		let showPrefix = flat_null;
		let showFormatting = flat_null;
		let linePrefix = flat_null;
		let lineSuffix = flat_null;
		let prefix = flat_null;
		let suffix = flat_null;
		let prefixSeparator = flat_null;
		let forceLevel = flat_null;
		let forceShowPrefixEnv = flat_null;
		let forceShowFormattingEnv = flat_null;
		let forceLinePrefixEnv = flat_null;
		let forceLineSuffixEnv = flat_null;
		let forcePrefixEnv = flat_null;
		let forceSuffixEnv = flat_null;
		let forcePrefixSeparatorEnv = flat_null;
		if ((labelLoggingLevels = System.getEnv(FlatString.construct5("LOGGING_LABEL_LOGGING_LEVELS"))) !== flat_null) {
			labelLoggingLevels.split0(FlatString.construct5(";")).__callExtension(StreamListExtensions.List_stream, []).map((_x) => {
					return _x.split0(FlatString.construct5(":"));
			}).filter((_x) => {
					return _x.accessor_count() === 2;
			}).forEach((_x) => {
					Logger.setLoggingLevel1(_x.get(0), Logger.getLevelFromString(_x.get(1)));
			});
		}
		if ((traceLoggingLabels = System.getEnv(FlatString.construct5("LOGGING_TRACE_LABEL_LOGGING_LEVELS"))) !== flat_null) {
			traceLoggingLabels.split0(FlatString.construct5(";")).forEach1((_x, _i, _array) => {
					Logger.setLoggingLevel1(_x, Logger.TRACE);
			});
		}
		if ((debugLoggingLabels = System.getEnv(FlatString.construct5("LOGGING_DEBUG_LABEL_LOGGING_LEVELS"))) !== flat_null) {
			debugLoggingLabels.split0(FlatString.construct5(";")).forEach1((_x, _i, _array) => {
					Logger.setLoggingLevel1(_x, Logger.DEBUG);
			});
		}
		if ((infoLoggingLabels = System.getEnv(FlatString.construct5("LOGGING_INFO_LABEL_LOGGING_LEVELS"))) !== flat_null) {
			infoLoggingLabels.split0(FlatString.construct5(";")).forEach1((_x, _i, _array) => {
					Logger.setLoggingLevel1(_x, Logger.INFO);
			});
		}
		if ((warnLoggingLabels = System.getEnv(FlatString.construct5("LOGGING_WARN_LABEL_LOGGING_LEVELS"))) !== flat_null) {
			warnLoggingLabels.split0(FlatString.construct5(";")).forEach1((_x, _i, _array) => {
					Logger.setLoggingLevel1(_x, Logger.WARN);
			});
		}
		if ((errorLoggingLabels = System.getEnv(FlatString.construct5("LOGGING_ERROR_LABEL_LOGGING_LEVELS"))) !== flat_null) {
			errorLoggingLabels.split0(FlatString.construct5(";")).forEach1((_x, _i, _array) => {
					Logger.setLoggingLevel1(_x, Logger.ERROR);
			});
		}
		if ((fatalLoggingLabels = System.getEnv(FlatString.construct5("LOGGING_FATAL_LABEL_LOGGING_LEVELS"))) !== flat_null) {
			fatalLoggingLabels.split0(FlatString.construct5(";")).forEach1((_x, _i, _array) => {
					Logger.setLoggingLevel1(_x, Logger.FATAL);
			});
		}
		if ((offLoggingLabels = System.getEnv(FlatString.construct5("LOGGING_OFF_LABEL_LOGGING_LEVELS"))) !== flat_null) {
			offLoggingLabels.split0(FlatString.construct5(";")).forEach1((_x, _i, _array) => {
					Logger.setLoggingLevel1(_x, Logger.OFF);
			});
		}
		if ((level = System.getEnv(FlatString.construct5("LOGGING_DEFAULT_LOGGING_LEVEL"))) !== flat_null) {
			Logger.mutator_defaultLoggingLevel(Logger.getLevelFromString(level));
		}
		if ((showPrefix = System.getEnv(FlatString.construct5("LOGGING_DEFAULT_SHOW_PREFIX"))) !== flat_null) {
			Logger.defaultShowPrefix = Bool.construct(Bool.fromString(showPrefix));
		}
		if ((showFormatting = System.getEnv(FlatString.construct5("LOGGING_DEFAULT_SHOW_FORMATTING"))) !== flat_null) {
			Logger.defaultShowFormatting = Bool.construct(Bool.fromString(showFormatting));
		}
		if ((linePrefix = System.getEnv(FlatString.construct5("LOGGING_DEFAULT_LINE_PREFIX"))) !== flat_null) {
			Logger.defaultLinePrefix = linePrefix;
		}
		if ((lineSuffix = System.getEnv(FlatString.construct5("LOGGING_DEFAULT_LINE_SUFFIX"))) !== flat_null) {
			Logger.defaultLineSuffix = lineSuffix;
		}
		if ((prefix = System.getEnv(FlatString.construct5("LOGGING_DEFAULT_PREFIX"))) !== flat_null) {
			Logger.defaultPrefix = prefix;
		}
		if ((suffix = System.getEnv(FlatString.construct5("LOGGING_DEFAULT_SUFFIX"))) !== flat_null) {
			Logger.defaultSuffix = suffix;
		}
		if ((prefixSeparator = System.getEnv(FlatString.construct5("LOGGING_DEFAULT_PREFIX_SEPARATOR"))) !== flat_null) {
			Logger.defaultPrefixSeparator = prefixSeparator;
		}
		if ((forceLevel = System.getEnv(FlatString.construct5("LOGGING_FORCE_LOGGING_LEVEL"))) !== flat_null) {
			Logger.forceLoggingLevel = FlatInt.construct(Logger.getLevelFromString(forceLevel));
		}
		if ((forceShowPrefixEnv = System.getEnv(FlatString.construct5("LOGGING_FORCE_SHOW_PREFIX"))) !== flat_null) {
			Logger.forceShowPrefix = Bool.construct(Bool.fromString(forceShowPrefixEnv));
		}
		if ((forceShowFormattingEnv = System.getEnv(FlatString.construct5("LOGGING_FORCE_SHOW_FORMATTING"))) !== flat_null) {
			Logger.forceShowFormatting = Bool.construct(Bool.fromString(forceShowFormattingEnv));
		}
		if ((forceLinePrefixEnv = System.getEnv(FlatString.construct5("LOGGING_FORCE_LINE_PREFIX"))) !== flat_null) {
			Logger.forceLinePrefix = forceLinePrefixEnv;
		}
		if ((forceLineSuffixEnv = System.getEnv(FlatString.construct5("LOGGING_FORCE_LINE_SUFFIX"))) !== flat_null) {
			Logger.forceLineSuffix = forceLineSuffixEnv;
		}
		if ((forcePrefixEnv = System.getEnv(FlatString.construct5("LOGGING_FORCE_PREFIX"))) !== flat_null) {
			Logger.forcePrefix = forcePrefixEnv;
		}
		if ((forceSuffixEnv = System.getEnv(FlatString.construct5("LOGGING_FORCE_SUFFIX"))) !== flat_null) {
			Logger.forceSuffix = forceSuffixEnv;
		}
		if ((forcePrefixSeparatorEnv = System.getEnv(FlatString.construct5("LOGGING_FORCE_PREFIX_SEPARATOR"))) !== flat_null) {
			Logger.forcePrefixSeparator = forcePrefixSeparatorEnv;
		}
	}
}

var FlatLong0_Flat_init_static_initialized = false;
function FlatLong0_Flat_init_static() {
	if (!FlatLong0_Flat_init_static_initialized) {
		FlatLong0_Flat_init_static_initialized = true;
		FlatLong.MAX_VALUE = 9223372036854775807;
		FlatLong.MIN_VALUE = -9223372036854775808;
		FlatLong.FILE_SIZES = FlatLong.generated275();
	}
}

var FlatMath0_Flat_init_static_initialized = false;
function FlatMath0_Flat_init_static() {
	if (!FlatMath0_Flat_init_static_initialized) {
		FlatMath0_Flat_init_static_initialized = true;
		FlatMath.PI = 3.141592653;
	}
}

var FlatString0_Flat_init_static_initialized = false;
function FlatString0_Flat_init_static() {
	if (!FlatString0_Flat_init_static_initialized) {
		FlatString0_Flat_init_static_initialized = true;
		FlatString.WHITESPACE = FlatString.generated276();
	}
}

var Stream0_Flat_init_static_initialized = false;
function Stream0_Flat_init_static() {
	if (!Stream0_Flat_init_static_initialized) {
		Stream0_Flat_init_static_initialized = true;
		Logger0_Flat_init_static();
		Logger1_Flat_init_static();
		Stream.log = Logger.construct(Stream.accessor__js_class(), undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined);
	}
}

var SyntaxStringFunctions0_Flat_init_static_initialized = false;
function SyntaxStringFunctions0_Flat_init_static() {
	if (!SyntaxStringFunctions0_Flat_init_static_initialized) {
		SyntaxStringFunctions0_Flat_init_static_initialized = true;
		SyntaxStringFunctions.WHITESPACE = FlatString.construct5(" \t\n\r").chars.toCharArray();
		SyntaxStringFunctions.EITHER_STATEMENT_END_CHARS = FlatString.construct5("\n;{}").chars.toCharArray();
		SyntaxStringFunctions.SYMBOLS_CHARS = FlatString.construct5("-+~!=%^&|*/\\><,\"'[]{};:?()").chars.toCharArray();
		SyntaxStringFunctions.ALL_SYMBOLS_CHARS = FlatString.construct5("-+~!=%^&|*/\\><,\"'[]{};:?().\#$").chars.toCharArray();
		SyntaxStringFunctions.STMT_PRE_CONT_CHARS = FlatString.construct5("-+~!=%^&|*/\\><,.[").chars.toCharArray();
		SyntaxStringFunctions.STMT_POST_CONT_CHARS = FlatString.construct5("-+~!=%^&|*/\\><,.]").chars.toCharArray();
		SyntaxStringFunctions.INVALID_DECLARATION_CHARS = FlatString.construct5("-+~!=%^|/\"\\'{};()").chars.toCharArray();
	SyntaxStringFunctions.WORD_BOUNDARIES = FlatString.construct5(" \t\n\r-+~!=%^&|*/\\><,\"'[]{};:?()").chars.toCharArray();
}}

var System0_Flat_init_static_initialized = false;
function System0_Flat_init_static() {
	if (!System0_Flat_init_static_initialized) {
		System0_Flat_init_static_initialized = true;
		System.TARGET = FlatString.construct5("Javascript");
		System.WINDOWS = 1;
		System.LINUX = 2;
		System.MAC_OSX = 3;
		System.ANDROID = 4;
		System.IOS = 5;
		System.UNKNOWN = 6;
		System.overheadTimer = CumulativeTimer.construct();
	}
}

var System1_Flat_init_static_initialized = false;
function System1_Flat_init_static() {
	if (!System1_Flat_init_static_initialized) {
		System1_Flat_init_static_initialized = true;
		if (typeof process !== 'undefined') {
			switch (process.platform) {
				case "win32": {
					System.OS = FlatString.construct5("Windows");
					System.OS_INT = System.WINDOWS;
					break;
				}
				case "linux": {
					System.OS = FlatString.construct5("Linux");
					System.OS_INT = System.LINUX;
					break;
				}
				case "darwin": {
					System.OS = FlatString.construct5("Mac OS X");
					System.OS_INT = System.MAC_OSX;
					break;
				}
				default: {
					System.OS = FlatString.construct5("Unknown");
					System.OS_INT = System.UNKNOWN;
				}
			}
		} else if (typeof navigator !== 'undefined') {
			if (navigator.userAgent.indexOf("Win") !== -1) {
				System.OS = FlatString.construct5("Windows");
				System.OS_INT = System.WINDOWS;
			} else if (navigator.userAgent.indexOf("Mac") !== -1) {
				System.OS = FlatString.construct5("Mac OS X");
				System.OS_INT = System.MAC_OSX;
			} else if (navigator.userAgent.indexOf("Linux") !== -1) {
				System.OS = FlatString.construct5("Linux");
				System.OS_INT = System.LINUX;
			} else if (navigator.userAgent.indexOf("Android") !== -1) {
				System.OS = FlatString.construct5("Android");
				System.OS_INT = System.ANDROID;
			} else if (navigator.userAgent.indexOf("like Mac") !== -1) {
				System.OS = FlatString.construct5("iOS");
				System.OS_INT = System.IOS;
			} else {
				System.OS = FlatString.construct5("Unknown");
				System.OS_INT = System.UNKNOWN;
			}
		}
	}
}

var Test0_Flat_init_static_initialized = false;
function Test0_Flat_init_static() {
	if (!Test0_Flat_init_static_initialized) {
		Test0_Flat_init_static_initialized = true;
		FlatConsole0_Flat_init_static();
		Test.out = FancyOutputStream.construct(undefined, undefined, undefined);
	}
}

var TestAsync0_Flat_init_static_initialized = false;
function TestAsync0_Flat_init_static() {
	if (!TestAsync0_Flat_init_static_initialized) {
		TestAsync0_Flat_init_static_initialized = true;
		FlatConsole0_Flat_init_static();
		TestAsync.out = FancyOutputStream.construct(undefined, undefined, undefined);
	}
}

var TestCase0_Flat_init_static_initialized = false;
function TestCase0_Flat_init_static() {
	if (!TestCase0_Flat_init_static_initialized) {
		TestCase0_Flat_init_static_initialized = true;
		TestCase.staticId = 0;
	}
}

App0_Flat_init_static();
Colorizer0_Flat_init_static();
FlatConsole0_Flat_init_static();
OutputStream0_Flat_init_static();
FlatDateTime0_Flat_init_static();
EscapeCode0_Flat_init_static();
EventStream0_Flat_init_static();
File0_Flat_init_static();
File1_Flat_init_static();
Import_Test0_Flat_init_static();
Import_TestSuite0_Flat_init_static();
FlatInt0_Flat_init_static();
Logger0_Flat_init_static();
Logger1_Flat_init_static();
FlatLong0_Flat_init_static();
FlatMath0_Flat_init_static();
FlatString0_Flat_init_static();
Stream0_Flat_init_static();
SyntaxStringFunctions0_Flat_init_static();
System0_Flat_init_static();
System1_Flat_init_static();
Test0_Flat_init_static();
TestAsync0_Flat_init_static();
TestCase0_Flat_init_static();

var flat_main_args = typeof process !== 'undefined' && process.argv ?
  FlatArray.jsStringArrayToFlatArray(process.argv.slice(1)) :
  FlatArray.construct();

if (typeof process !== 'undefined' && typeof process.on === 'function') {
	process.on('unhandledRejection', (reason, promise) => {
			console.error(reason);
			process.exit(1);
	});
}

Import_TestSuite.main(flat_main_args);
