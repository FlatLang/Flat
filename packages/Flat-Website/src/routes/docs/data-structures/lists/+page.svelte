<template lang="flat-html">
  <h1>LIST DATA STRUCTURES</h1>

  <p>
    Lists are arguably the most fundamental data structure in the Flat language. Not only do they
    form the foundation of ordered and unordered lists of data, they also form the foundation of the
    functional aspect of the language. The consistency of a common List trait datatype makes
    functional programming in Flat much more natural than if you would have had to cast to an
    IEnumerable datatype, like in C#, or a Stream, like in Java. The ability to take any list
    datatype (Arrays, Stacks, Queues, etc.) and right then and there know that you have a vast array
    (pun intended) of functional capabilities available to use is a very powerful part of what makes
    Flat so great.
  </p>

  <p>
    So how do you use these Lists? Here is an example of creating a list of letters, sorting them,
    then mapping them to their corresponding index in the English alphabet.
  </p>

  <pre>
    <code class="language-flat">
      {`
        let myLetters = ["X", "W", "Z", "C", "B", "A", "Y", "D"]

        Console.writeLine(myLetters) // [X, W, Z, C, B, A, Y, D]

        myList.sort()

        Console.writeLine(myLetters) // [A, B, C, D, W, X, Y, Z]

        let myNumbers = myLetters.map(x => ((Int)x.chars[0] - 'A') + 1)

        Console.writeLine(myNumbers) // [1, 2, 3, 4, 23, 24, 25, 26]
      `}
    </code>
  </pre>
</template>
