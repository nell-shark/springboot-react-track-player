import { Footer } from "@components/Footer";
import { Navigation } from "@components/Navigation";
import { useTitle } from "@hooks/useTitle";
import { Page } from "@interfaces/page";

interface AboutProps extends Page {}

export function About({ title }: AboutProps) {
  useTitle(title);

  return (
    <>
      <Navigation />
      <Footer />
    </>
  );
}
