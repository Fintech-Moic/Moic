export default function layout({ children }: { children: React.ReactNode }) {
  return (
    <div className="h-screen w-screen flex flex-col justify-evenly items-center">
      {children}
    </div>
  );
}
