export default function layout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex flex-col justify-between h-screen">{children}</div>
  );
}
