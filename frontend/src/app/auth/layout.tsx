export default function layout({ children }: { children: React.ReactNode }) {
  return (
    <div className="h-screen flex flex-col justify-evenly items-center">
      {children}
    </div>
  );
}
