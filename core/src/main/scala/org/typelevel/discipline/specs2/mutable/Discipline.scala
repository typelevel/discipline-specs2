/*
 * Copyright (c) 2019-2021 Typelevel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.typelevel.discipline
package specs2.mutable

import org.specs2.mutable.SpecificationLike
import org.specs2.ScalaCheck
import org.specs2.scalacheck.Parameters
import org.specs2.specification.core.Fragments

import scala.language.implicitConversions

trait Discipline extends ScalaCheck { self: SpecificationLike =>

  def checkAll(name: String, ruleSet: Laws#RuleSet)(implicit p: Parameters) = {
    s"""${ruleSet.name} laws must hold for $name""".txt
    br
    t
    Fragments.foreach(ruleSet.all.properties.toList.zipWithIndex) { case ((id, prop), n) =>
      addFragment(fragmentFactory.example(id, check(prop, p, defaultFreqMapPretty)))
      if (n != ruleSet.all.properties.toList.size - 1) br
      else bt
    }
    br
  }

}
