package org.loudkicks

import java.io.PrintWriter

trait ConsoleApp {
  def prompt: String
  def output: PrintWriter
  def input: Iterator[String]
  def parse(line: String):Response

  def ready() = {
    output.print(prompt)
    output.flush()
  }

  def run() = {
    ready()
    for {
      line <- input
    } write(parse(line))
  }

  def write(response: Response) = {
    for (line <- response.lines) output.println(line)
    ready()
  }
}
